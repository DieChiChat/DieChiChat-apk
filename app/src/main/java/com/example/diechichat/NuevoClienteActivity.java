package com.example.diechichat;

import android.os.Bundle;

import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.databinding.ActivityNuevoClienteBinding;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;

public class NuevoClienteActivity extends AppCompatActivity implements NuevoCienteFragment.NuevoCliFragmentInterface {
    private ActivityNuevoClienteBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_nuevo_cliente);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        binding=ActivityNuevoClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC=((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV)).getNavController();

    }

    @Override
    public void onAceptarNuevoFrag() {

    }

    @Override
    public void onCancelarNuevoFrag() {
        finish();
    }
}