package com.example.diechichat.vista;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.databinding.ActivityMisClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vista.fragmentos.MisClientesFragment;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MisClientesActivity extends AppCompatActivity implements
        MisClientesFragment.MisClientesFragmentInterface,
        NuevoCienteFragment.NuevoCliFragmentInterface {
    private ActivityMisClientesBinding binding;
    private NavController mNavC;
    private ClienteViewModel mCliVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisClientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mCliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misclientesFragCV)).getNavController();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVerClienteFrag(Cliente cli) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteVer",cli);
        bundleCli.putInt("op", NuevoCienteFragment.OP_EDITAR);
        mNavC.navigate(R.id.action_mis_lientes_to_nuevoCienteFragment,bundleCli);
    }

    @Override
    public void onAddDietaFrag(Cliente cli) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteAddDieta",cli);
    //    mNavC.navigate(R.id.action);
    }

    @Override
    public void onAceptarNuevoFrag(int op, Cliente c) {

    }

    @Override
    public void onCancelarNuevoFrag() {

    }

    @Override
    public void onAbrirCamaraFrag() {

    }
}
