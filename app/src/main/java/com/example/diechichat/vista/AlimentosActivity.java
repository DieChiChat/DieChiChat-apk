package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityAlimentosBinding;

public class AlimentosActivity extends AppCompatActivity {
    private ActivityAlimentosBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlimentosBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_alimentos);

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.alimentosFragCV)).getNavController();
    }
}