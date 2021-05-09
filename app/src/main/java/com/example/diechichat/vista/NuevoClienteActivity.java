package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityNuevoClienteBinding;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;

public class NuevoClienteActivity extends AppCompatActivity implements NuevoCienteFragment.NuevoCliFragmentInterface, DlgSeleccionFecha.DlgSeleccionFechaListener {
    private ActivityNuevoClienteBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_nuevo_cliente);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        binding = ActivityNuevoClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV)).getNavController();

    }

    @Override
    public void onAceptarNuevoFrag() {

    }

    @Override
    public void onCancelarNuevoFrag() {
        finish();
    }

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, String fecha) {
        // NuevoCienteFragment frag = (NuevoCienteFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV); --> No funciona
//        if(null!=frag){
//            frag.setFecha(fecha);
//        }
    }

    @Override
    public void onDlgSeleccionFechaCancel(DialogFragment dialog) {
        // NuevoCienteFragment frag = (NuevoCienteFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV); --> No funciona
//        if(null!=frag){
//            frag.setFecha("");
//        }
    }
}
