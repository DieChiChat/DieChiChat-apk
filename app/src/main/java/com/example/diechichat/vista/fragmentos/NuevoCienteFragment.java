package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.dialogos.DlgConfirmacion;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NuevoCienteFragment extends Fragment implements
        DlgSeleccionFecha.DlgSeleccionFechaListener,
        DlgConfirmacion.DlgConfirmacionListener {

    private FragmentNuevoClienteBinding binding;
    private NuevoCliFragmentInterface mListener;
    private Cliente c;
    private Nutricionista nutri;
    private int mLogin;
    private ClienteViewModel cliVM;

    private int mOp;    // Operación a realizar
    public static final int OP_CREAR = 0;
    public static final int OP_EDITAR = 1;
    private static final String TAG_SELECCION_FECHA = "tagSeleccionFecha";

    private Bitmap fotoBitmap;

    public interface NuevoCliFragmentInterface {
        void onAceptarNuevoFrag(int op, Cliente c);

        void onCancelarNuevoFrag();

        void onAbrirCamaraFrag();

        void onEliminarClienteFrag(Cliente cliente);

        void onEditadoSinHabilitarFrag();
    }

    public NuevoCienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NuevoCliFragmentInterface) {
            mListener = (NuevoCliFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NuevoCliFragmentInterface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOp = getArguments().getInt("opcion");
            c = getArguments().getParcelable("cliente");
            nutri= getArguments().getParcelable("loginNutric");
        }

        cliVM = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        if (c == null) {
            c = cliVM.getLogin();
            mOp = cliVM.getOpcion();
            cliVM.setEsCliente(true);
        }

        cliVM.getmFechaDlg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String fecha) {
                binding.etFecNac.setText(fecha);
            }
        });

        cliVM.getFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap foto) {
                fotoBitmap = foto;
                binding.ivFoto.setImageBitmap(foto);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mOp == OP_CREAR) {
            setHasOptionsMenu(false);
        } else if (mOp == OP_EDITAR) {
            setHasOptionsMenu(true);
        }
        binding = FragmentNuevoClienteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.numPickerAltura.setEnabled(true);
        binding.numPickerPeso.setEnabled(true);
        binding.numPickerAltura.setMinValue(150);
        binding.numPickerAltura.setMaxValue(300);
        binding.numPickerPeso.setMinValue(45);
        binding.numPickerPeso.setMaxValue(400);
        binding.numPickerPeso.setWrapSelectorWheel(true);
        binding.numPickerAltura.setWrapSelectorWheel(true);

        binding.tvId.setText("");

        binding.btAceptar.setOnClickListener(btAceptar_onClickListener);
        binding.btCancelar.setOnClickListener(btCancelar_onClickListener);
        binding.btFecnac.setOnClickListener(btFecnac_OnClickListener);
        binding.btVerContrasena.setOnClickListener(btVerContrasena_OnClickListener);

        if (mOp != -1) {
            switch (mOp) {
                case OP_EDITAR:
                    binding.tvFoto.setText(R.string.tvFotoPerfil);
                    Bundle b = getArguments();
                    if (b != null || c != null) {
                        habilitarCampos(false);
                        if (cliVM.getLogin() != null) {
                            c = cliVM.getLogin();
                        } else {
                            c = b.getParcelable("cliente");
                        }
                        if (c != null) {
                            binding.etNuevoNombre.setText(c.getNombre());
                            binding.etNuevoApellidos.setText(c.getApellidos());
                            binding.etNuevoUsuario.setText(c.getUsuario());
                            binding.etNuevoContrasena.setText(c.getContrasena());
                            binding.etFecNac.setText(c.getFechaFormat());
                            binding.numPickerPeso.setValue((int) c.getPeso());
                            binding.numPickerAltura.setValue((int) c.getAltura());
                            if (!mostrarImagenStorage(c)) {
                                binding.ivFoto.setImageResource(R.drawable.foto_camara_icono_round);
                            }
                            binding.tvId.setText(c.getId());
                            binding.tvId.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;
                case OP_CREAR:
                    binding.tvFoto.setText(R.string.tvFotoAsignar);
                    binding.tvId.setVisibility(View.INVISIBLE);
//                    binding.btVerContrasena.setVisibility(View.INVISIBLE);
                    binding.etNuevoContrasena.setTransformationMethod(new PasswordTransformationMethod());
                    habilitarCampos(true);
                    break;
            }
        }

        binding.numPickerAltura.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                esconderTeclado(view);
            }
        });

        binding.numPickerPeso.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                esconderTeclado(view);
            }
        });

        if(nutri==null){
            binding.ivFoto.setOnClickListener(btFoto_OnClickListener);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_perfil_cliente, menu);
        if(cliVM.getEsCliente()) {
            menu.findItem(R.id.menuEliminar).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuEditarPerfil) {
            if (binding.etNuevoNombre.isEnabled()) {
                habilitarCampos(false);
            } else {
                habilitarCampos(true);
            }
        } else if (item.getItemId() == R.id.menuEliminar) {
            mListener.onEliminarClienteFrag(c);
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDlgSalir() {
        //Lanzamos DlgConfirmacion
        Bundle bundle = new Bundle();
        bundle.putInt("titulo", R.string.app_name);
        bundle.putInt("mensaje", R.string.msg_DlgConfirmacion_Salir);
        bundle.putString("tag", "tagConfirmacion_Salir");
    }

    View.OnClickListener btAceptar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            if (mListener != null) {
                if (binding.etNuevoNombre.isEnabled()) {
                    if (!binding.etNuevoNombre.getText().toString().equals("") &&
                            !binding.etNuevoApellidos.getText().toString().equals("") &&
                            !binding.etNuevoUsuario.getText().toString().equals("") &&
                            !binding.etNuevoContrasena.getText().toString().equals("") &&
                            !binding.etFecNac.getText().toString().equals("")) {

                        // Creación nuevo cliente
                        c = new Cliente();
                        c.setNombreCompleto(binding.etNuevoNombre.getText().toString() + " " + binding.etNuevoApellidos.getText().toString());
                        c.setNombre(binding.etNuevoNombre.getText().toString());
                        c.setApellidos(binding.etNuevoApellidos.getText().toString());
                        c.setUsuario(binding.etNuevoUsuario.getText().toString());
                        c.setContrasena(binding.etNuevoContrasena.getText().toString());
                        c.setPeso(binding.numPickerPeso.getValue());
                        c.setAltura(binding.numPickerAltura.getValue());
                        c.setIdAdmin(mLogin);
                        c.setId(binding.tvId.getText().toString().equals("") ? generarId() : binding.tvId.getText().toString());
                        c.setFechaFormat((binding.etFecNac.getText().toString().equals("") ? "" : binding.etFecNac.getText().toString()));

                        mListener.onAceptarNuevoFrag(mOp, c);
                        if(mOp == OP_EDITAR) {
                            habilitarCampos(false);
                        }
                    } else {
                        mListener.onAceptarNuevoFrag(-1, null);  // Faltan Datos Obligatorios
                    }
                } else {
                    mListener.onEditadoSinHabilitarFrag();
                }
            }
        }
    };
    View.OnClickListener btCancelar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);

            if (mListener != null) {
                mListener.onCancelarNuevoFrag();
            }
        }
    };
    View.OnClickListener btFecnac_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);

            DialogFragment dialogFragmentFecha = new DlgSeleccionFecha();
            dialogFragmentFecha.show(getParentFragmentManager(), TAG_SELECCION_FECHA);
        }
    };

    View.OnClickListener btFoto_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            mListener.onAbrirCamaraFrag();
        }
    };

    View.OnClickListener btVerContrasena_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            if (binding.etNuevoContrasena.getTransformationMethod() == null) {
                binding.etNuevoContrasena.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                binding.etNuevoContrasena.setTransformationMethod(null);
            }
        }
    };

    /**
     * MÉTODOS DIÁLOGO SELECCIÓN FECHA
     *****************************************/

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        binding.etFecNac.setText(fecha);
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        binding.etFecNac.setText("");
    }


    /**
     * MÉTODOS DIÁLOGO ELIMINAR
     *****************************************/

    @Override
    public void onDlgConfirmacionPositiveClick(DialogFragment dialog) {
        mListener.onEliminarClienteFrag(c);
    }

    @Override
    public void onDlgConfirmacionNegativeClick(DialogFragment dialog) {
        mListener.onEliminarClienteFrag(null);
    }

    /*********************************************************/
    public String generarId() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String s = sdf.format(Calendar.getInstance().getTime());
        String fecha = s.substring(6, 10) + s.substring(3, 5) + s.substring(0, 2);

        Calendar calendario = Calendar.getInstance();
        String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + String.valueOf(calendario.get(Calendar.MINUTE)) + String.valueOf(calendario.get(Calendar.SECOND));

        return mLogin + fecha + hora;
    }

    public void esconderTeclado(View v) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public boolean mostrarImagenStorage(Cliente cli) {
        final boolean[] hayImagen = {false};
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://diechichat.appspot.com").child("fotosClientes/" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");
        try {
            final File localFile = File.createTempFile("imagen" + cli.getId() + ".jpeg", "jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.ivFoto.setImageBitmap(bitmap);
                    hayImagen[0] = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    hayImagen[0] = false;
                }
            });
        } catch (IOException e) {
        }
        return hayImagen[0];
    }

    public void habilitarCampos(boolean habilitado) {

        if (habilitado) {
            binding.etNuevoNombre.setEnabled(true);
            binding.etNuevoApellidos.setEnabled(true);
            binding.etFecNac.setEnabled(true);
            binding.numPickerAltura.setEnabled(true);
            binding.numPickerPeso.setEnabled(true);
            binding.btFecnac.setEnabled(true);
            binding.etNuevoUsuario.setEnabled(true);
            binding.etNuevoContrasena.setEnabled(true);
            if(nutri == null){
                if (mOp==OP_CREAR){
                    binding.tvFoto.setText(R.string.tvFotoAsignar);
                }else{
                    binding.tvFoto.setText(R.string.tvFotoCambiar);
                }
            }

        } else {
            binding.etNuevoNombre.setEnabled(false);
            binding.etNuevoApellidos.setEnabled(false);
            binding.etNuevoUsuario.setEnabled(false);
            binding.etNuevoContrasena.setEnabled(false);
            binding.etFecNac.setEnabled(false);
            binding.numPickerAltura.setEnabled(false);
            binding.numPickerPeso.setEnabled(false);
            binding.btFecnac.setEnabled(false);
            binding.etNuevoContrasena.setTransformationMethod(new PasswordTransformationMethod());
        }
    }
}