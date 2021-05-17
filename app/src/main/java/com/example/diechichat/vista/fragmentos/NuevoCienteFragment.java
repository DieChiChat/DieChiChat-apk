package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
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
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class NuevoCienteFragment extends Fragment implements
        DlgSeleccionFecha.DlgSeleccionFechaListener {

    private FragmentNuevoClienteBinding binding;
    private NuevoCliFragmentInterface mListener;
    private Cliente c;
    private int mLogin;

    private int mOp;    // Operación a realizar
    public static final int OP_CREAR = 1;
    public static final int OP_EDITAR = 2;

    private Bitmap fotoBitmap;

    public interface NuevoCliFragmentInterface {
        void onAceptarNuevoFrag(Cliente c);
        void onCancelarNuevoFrag();
        void onAbrirCamaraFrag();
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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mOp = getArguments().getInt("op");
            c = getArguments().getParcelable("cli");
            mLogin = getArguments().getInt("login");
        }
        ClienteViewModel cliVM= new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
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
        binding = FragmentNuevoClienteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.numPickerAltura.setEnabled(true);
        binding.numPickerPeso.setEnabled(true);
        binding.numPickerAltura.setMinValue(0);
        binding.numPickerAltura.setMaxValue(300);
        binding.numPickerPeso.setMinValue(0);
        binding.numPickerPeso.setMaxValue(400);
        binding.numPickerPeso.setWrapSelectorWheel(true);
        binding.numPickerAltura.setWrapSelectorWheel(true);

        binding.btAceptar.setOnClickListener(btAceptar_onClickListener);
        binding.btCancelar.setOnClickListener(btCancelar_onClickListener);
        binding.btFecnac.setOnClickListener(btFecnac_OnClickListener);

        if (mOp != -1) {    // MtoIncsFragment requiere una operación válida!!
            switch (mOp) {
                case OP_EDITAR:
                    Bundle b = getArguments();
                    if (b != null) {
                        c = b.getParcelable("cli");
                        binding.etNuevoNombre.setText(c.getNombre());
                        binding.etNuevoApellidos.setText(c.getApellidos());
                        binding.etNuevoUsuario.setText(c.getUsuario());
                        binding.etNuevoContrasena.setText(c.getContrasena());
                        binding.numPickerPeso.setValue((int) c.getPeso());
                        binding.numPickerAltura.setValue((int) c.getAltura());

                    }
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

        binding.ivFoto.setOnClickListener(btFoto_OnClickListener);

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


    View.OnClickListener btAceptar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            if (mListener != null) {
                if (!binding.etNuevoNombre.getText().toString().equals("") &&
                        !binding.etNuevoApellidos.getText().toString().equals("") &&
                        !binding.etNuevoUsuario.getText().toString().equals("") &&
                        !binding.etNuevoContrasena.getText().toString().equals("") &&
                        !binding.etFecNac.getText().toString().equals("")) {

                    if (binding.numPickerPeso.getValue() == 1) {            //Peso con valor 0
                        Snackbar.make(binding.getRoot(), R.string.msg_peso, Snackbar.LENGTH_SHORT).show();
                    } else if (binding.numPickerAltura.getValue() == 2) {   //Altura con valor 0
                        Snackbar.make(binding.getRoot(), R.string.msg_altura, Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Creación un nuevo cliente
                        c = new Cliente();
                        c.setNombreCompleto(binding.etNuevoNombre.getText().toString() + " " + binding.etNuevoApellidos.getText().toString());
                        c.setUsuario(binding.etNuevoUsuario.getText().toString());
                        c.setContrasena(binding.etNuevoContrasena.getText().toString());
                        c.setPeso(binding.numPickerPeso.getValue());
                        c.setAltura(binding.numPickerAltura.getValue());
                        c.setIdAdmin(mLogin);
                        c.setId(generarId());
                        c.setFechaFormat((binding.etFecNac.getText().toString().equals("") ? "" : binding.etFecNac.getText().toString()));

                        mListener.onAceptarNuevoFrag(c);
                    }
                } else {
                    mListener.onAceptarNuevoFrag(null);  // Faltan Datos Obligatorios
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
            dialogFragmentFecha.show(getParentFragmentManager(), "tagSeleccionFecha");
        }
    };

    View.OnClickListener btFoto_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            mListener.onAbrirCamaraFrag();
        }
    };

    /** MÉTODOS DIÁLOGO SELECCIÓN FECHA*****************************************/

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        binding.etFecNac.setText(fecha);
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        binding.etFecNac.setText("");
    }

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

    public void mostrarImagenStorage(Cliente cli) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://diechichat.appspot.com").child("fotosClientes" + cli.getId()).child("imagen" + cli.getId() + ".jpeg");
        try {
            final File localFile = File.createTempFile("imagen" + cli.getId() + ".jpeg", "jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.ivFoto.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}
    }

}
