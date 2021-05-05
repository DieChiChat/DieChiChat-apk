package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;

public class NuevoCienteFragment extends Fragment {
    private FragmentNuevoClienteBinding binding;
    private NuevoCliFragmentInterface mListener;

    public interface NuevoCliFragmentInterface{
        void onAceptarNuevoFrag();
        void onCancelarNuevoFrag();
    }

    public NuevoCienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NuevoCliFragmentInterface){
            mListener= (NuevoCliFragmentInterface) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement NuevoCliFragmentInterface");
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments()!= null){

        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentNuevoClienteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.numPickerEdad.setEnabled(true);
        binding.numPickerPeso.setEnabled(true);
        binding.numPickerEdad.setMinValue(0);
        binding.numPickerEdad.setMaxValue(100);
        binding.numPickerPeso.setMinValue(20);
        binding.numPickerPeso.setMaxValue(400);
        binding.numPickerPeso.setWrapSelectorWheel(true);
        binding.numPickerEdad.setWrapSelectorWheel(true);

        binding.btAceptar.setOnClickListener(btAceptar_onClickListener);
        binding.btCancelar.setOnClickListener(btCancelar_onClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
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
            mListener.onAceptarNuevoFrag();
        }
    };
    View.OnClickListener btCancelar_onClickListener= new View.OnClickListener() {
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
}
