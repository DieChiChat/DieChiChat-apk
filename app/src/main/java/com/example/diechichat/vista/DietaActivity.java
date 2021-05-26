package com.example.diechichat.vista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.diechichat.databinding.ActivityMisClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vista.fragmentos.DietaFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.databinding.ActivityDietaBinding;

import com.example.diechichat.R;

public class DietaActivity extends AppCompatActivity
    implements DietaFragment.DietaFragmentInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDietaBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDietaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragDieta)).getNavController();

    }

    @Override
    public void onAsignarDieta(Cliente c, int opcion) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteAddDieta", c);
        bundleCli.putInt("op", opcion);
//        TODO: Navegación falla
//        mNavC.navigate(R.id.action_fragment_clientes_to_fragment_alimentos, bundleCli);
    }
}