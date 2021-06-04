package com.example.diechichat.vista.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.databinding.FragmentHomeBinding;
import com.example.diechichat.vistamodelo.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private MainViewModel mainVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mainVM = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Calendar calendario = Calendar.getInstance();
        String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + String.valueOf(calendario.get(Calendar.MINUTE)) + String.valueOf(calendario.get(Calendar.SECOND));

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
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
