package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentDietaBinding;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;

import org.jetbrains.annotations.NotNull;

public class DietaFragment extends Fragment {

    private FragmentDietaBinding binding;
    private DietaFragment.DietaFragmentInterface mListener;
    private Cliente cliUsuario;
    private Nutricionista nutriUsuario;

    private Cliente cli;

    public static final int OP_DESAYUNO = 0;
    public static final int OP_COMIDA = 1;
    public static final int OP_CENA = 2;
    public static final int OP_OTROS = 3;

    public DietaFragment() {
        // Required empty public constructor
    }


    public interface DietaFragmentInterface {
        void onAsignarDieta(Cliente c, int opcion);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NuevoCienteFragment.NuevoCliFragmentInterface) {
            mListener = (DietaFragment.DietaFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DietaFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Object o = getArguments().getParcelable("usuario");
            if(o instanceof Nutricionista) {
                nutriUsuario = (Nutricionista) o;
                getArguments().getParcelable("clienteAddDieta");
            } else if(o instanceof Cliente) {
                cliUsuario = (Cliente) o;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDietaBinding.inflate(inflater, container, false);

        if(nutriUsuario != null) {
            binding.btAddDesayuno.setVisibility(View.INVISIBLE);
            binding.btAddComida.setVisibility(View.INVISIBLE);
            binding.btAddCena.setVisibility(View.INVISIBLE);
            binding.btAddOtros.setVisibility(View.INVISIBLE);
        }

        if(cliUsuario != null) {
            binding.btAddDesayuno.setVisibility(View.INVISIBLE);
            binding.btAddComida.setVisibility(View.INVISIBLE);
            binding.btAddCena.setVisibility(View.INVISIBLE);
            binding.btAddOtros.setVisibility(View.INVISIBLE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btAddDesayuno.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddComida.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddCena.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddOtros.setOnClickListener(btAddDieta_onClickListener);
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

    View.OnClickListener btAddDieta_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == binding.btAddDesayuno) {
                mListener.onAsignarDieta(cli, OP_DESAYUNO);
            } else if(v == binding.btAddComida) {
                mListener.onAsignarDieta(cli, OP_COMIDA);
            } else if(v == binding.btAddCena) {
                mListener.onAsignarDieta(cli, OP_CENA);
            } else if(v == binding.btAddOtros) {
                mListener.onAsignarDieta(cli, OP_OTROS);
            }
        }
    };


}