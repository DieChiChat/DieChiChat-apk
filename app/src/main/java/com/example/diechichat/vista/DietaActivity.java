package com.example.diechichat.vista;

import android.os.Bundle;
import android.view.View;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.fragmentos.AlimentosFragment;
import com.example.diechichat.vista.fragmentos.DietaFragment;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.diechichat.databinding.ActivityDietaBinding;

import com.example.diechichat.R;

public class DietaActivity extends AppCompatActivity
    implements DietaFragment.DietaFragmentInterface, AlimentosFragment.AlimentoFragmentInterface {

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
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.dietaFragCV)).getNavController();

    }

    @Override
    public void onAsignarAlimento(Cliente c, int opcion) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteAddDieta", c);
        bundleCli.putInt("op", opcion);
        mNavC.navigate(R.id.action_fragment_dieta_to_fragment_alimentos, bundleCli);
    }

    @Override
    public void onBuscarAlimentoFrag(String alimento, View v) {

    }

    @Override
    public void onFinalizarSeleccionFrag(Alimento a, Cliente c) {

    }

    @Override
    public void onSeleccionarAlimentoFrag(Alimento a) {

    }
}