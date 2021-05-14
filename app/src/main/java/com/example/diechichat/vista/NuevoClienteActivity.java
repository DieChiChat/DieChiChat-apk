package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityNuevoClienteBinding;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.vista.dialogos.DlgSeleccionFecha;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.example.diechichat.vistamodelo.NutriViewModel;

public class NuevoClienteActivity extends AppCompatActivity implements NuevoCienteFragment.NuevoCliFragmentInterface, DlgSeleccionFecha.DlgSeleccionFechaListener {
    private ActivityNuevoClienteBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;

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
        cliVM= new ViewModelProvider(this).get(ClienteViewModel.class);
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
