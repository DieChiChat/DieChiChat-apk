package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentAlimentoSeleccionadoBinding;
import com.example.diechichat.databinding.FragmentAlimentosBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vistamodelo.AlimentoViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlimentoSeleccionadoFragment extends Fragment {

    private FragmentAlimentoSeleccionadoBinding binding;

    private AlimentoSeleccionadoFragmentInterface mListener;

    public AlimentoSeleccionadoFragment() {
        // Required empty public constructor
    }

    public interface AlimentoSeleccionadoFragmentInterface {
        void onAceptarAliSeleccionadoFrag();
        void onCancelarAliSeleccionadoFrag();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AlimentoSeleccionadoFragment.AlimentoSeleccionadoFragmentInterface) {
            mListener = (AlimentoSeleccionadoFragment.AlimentoSeleccionadoFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AlimentoSeleccionadoFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlimentoSeleccionadoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btAceptarAlimentos.setOnClickListener(btAceptarAlimento_onClickListener);
        binding.btCancelarAlimentos.setOnClickListener(btCancelarAlimento_onClickListener);
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


    /***************************************/
    View.OnClickListener btAceptarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onAceptarAliSeleccionadoFrag();
        }
    };

    View.OnClickListener btCancelarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onCancelarAliSeleccionadoFrag();
        }
    };

}