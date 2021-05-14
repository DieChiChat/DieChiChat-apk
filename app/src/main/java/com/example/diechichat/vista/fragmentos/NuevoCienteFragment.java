package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;

public class NuevoCienteFragment extends Fragment implements
        DlgSeleccionFecha.DlgSeleccionFechaListener {

    private FragmentNuevoClienteBinding binding;
    private NuevoCliFragmentInterface mListener;
    private Cliente c;
    private int mLogin;

    public interface NuevoCliFragmentInterface {
        void onAceptarNuevoFrag(Cliente c, int datos);  //  1 --> peso = 0 // 2 --> altura = 0
        void onCancelarNuevoFrag();
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
            mLogin = getArguments().getInt("login");
        }
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
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            if (mListener != null) {
                if (!binding.etNuevoNombre.getText().toString().equals("") &&
                        !binding.etNuevoApellidos.getText().toString().equals("") &&
                        !binding.etNuevoUsuario.getText().toString().equals("") &&
                        !binding.etNuevoContrasena.getText().toString().equals("")
                        /*&& !binding.etFecNac.getText().toString().equals("")*/) {
                    // Creación un nuevo cliente
                    c = new Cliente();
                    c.setNombreCompleto(binding.etNuevoNombre.getText().toString() + " " + binding.etNuevoApellidos.getText().toString());
                    c.setUsuario(binding.etNuevoUsuario.getText().toString());
                    c.setContrasena(binding.etNuevoContrasena.getText().toString());
                    c.setPeso(binding.numPickerPeso.getValue());
                    c.setAltura(binding.numPickerAltura.getValue());
                    c.setIdAdmin(mLogin);
                    c.setId(c.getFechaNacimiento() + 1);
                    c.setFechaNacimiento("15/09/1991");
                    String s = "Nombre: " + binding.etNuevoNombre.getText().toString()
                            + " Apellidos: " + binding.etNuevoApellidos.getText().toString()
                            + " Usuario: " + binding.etNuevoUsuario.getText().toString()
                            + " Contraseña: " + binding.etNuevoContrasena.getText().toString()
                            + " Altura: " + String.valueOf(binding.numPickerAltura.getValue())
                            + "Peso: " + String.valueOf(binding.numPickerPeso.getValue());

//                    c.setFechaFormat((binding.etFecNac.getText().toString().equals("") ? "" : ""));
                    if (binding.numPickerPeso.getValue() == 1) {            //Peso con valor 0
                        mListener.onAceptarNuevoFrag(c, 1);
                    } else if (binding.numPickerAltura.getValue() == 2) {   //Altura con valor 0
                        mListener.onAceptarNuevoFrag(c, 2);
                    } else {
                        mListener.onAceptarNuevoFrag(c, -1);
                    }
                } else {
                    mListener.onAceptarNuevoFrag(null, -1);  // Faltan Datos Obligatorios
                }
            }
        }
    };
    View.OnClickListener btCancelar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Ocultamos el teclado!
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            if (mListener != null) {
                mListener.onCancelarNuevoFrag();
            }
        }
    };
    View.OnClickListener btFecnac_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragmentFecha = new DlgSeleccionFecha();
            dialogFragmentFecha.show(getParentFragmentManager(), "tagSeleccionFecha");
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

}
