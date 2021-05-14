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
        binding = ActivityNuevoClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nuevoClienteFragCV)).getNavController();
      
        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
    }

    @Override
    public void onAceptarNuevoFrag(Cliente c, int datos) {
        if(c != null) {
            if (datos == 1) {
                Snackbar.make(binding.getRoot(), R.string.msg_peso, Snackbar.LENGTH_SHORT).show();
            } else if (datos == 2) {
                Snackbar.make(binding.getRoot(), R.string.msg_altura, Snackbar.LENGTH_SHORT).show();
            } else {
                cliVM.altaCliente(c).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean ok) {
                        Snackbar.make(binding.getRoot(), (ok) ? R.string.msg_altaCorrecta : R.string.msg_altaIncorrecta, Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
            mNavC.navigateUp();
        } else {
            Snackbar.make(binding.getRoot(), R.string.msg_datosObligatorios, Snackbar.LENGTH_SHORT).show();
        }
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
