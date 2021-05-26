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

import org.jetbrains.annotations.NotNull;

public class DietaFragment extends Fragment {

    private FragmentDietaBinding binding;
    private DietaFragment.DietaFragmentInterface mListener;

    public DietaFragment() {
        // Required empty public constructor
    }


    public interface DietaFragmentInterface {
        void onAsignarDieta();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDietaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}