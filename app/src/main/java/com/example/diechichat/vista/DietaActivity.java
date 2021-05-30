package com.example.diechichat.vista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.modelo.FatSecretGet;
import com.example.diechichat.modelo.FatSecretSearch;
import com.example.diechichat.modelo.Runable;
import com.example.diechichat.vista.fragmentos.AlimentosFragment;
import com.example.diechichat.vista.fragmentos.DietaFragment;
import com.example.diechichat.vistamodelo.AlimentoViewModel;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.diechichat.databinding.ActivityDietaBinding;

import com.example.diechichat.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DietaActivity extends AppCompatActivity
    implements DietaFragment.DietaFragmentInterface, AlimentosFragment.AlimentoFragmentInterface {

    private ActivityDietaBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;
    private AlimentoViewModel alimentoVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDietaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        alimentoVM = new ViewModelProvider(this).get(AlimentoViewModel.class);

        DietaActivity.Receptor receptor = new DietaActivity.Receptor();
        IntentFilter filter = new IntentFilter();
        filter.addAction("RESPUESTA_JSON");
        LocalBroadcastManager.getInstance(this).registerReceiver(receptor, filter);

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.dietaFragCV)).getNavController();

    }

    @Override
    public void onAsignarAlimento(Cliente c, int opcion) {
        Bundle bundleCli= new Bundle();
        bundleCli.putParcelable("clienteAddDieta", c);
        bundleCli.putInt("op", opcion);
        mNavC.navigate(R.id.action_fragment_dieta_to_fragment_alimentos, bundleCli);
    }


    /***************************************/

    public class Receptor extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("RESPUESTA_JSON") && intent.getParcelableArrayListExtra("json") != null) {
                ArrayList<Alimento> tAlimentos = intent.getParcelableArrayListExtra("json");
                alimentoVM.setListadoAlimentos(tAlimentos);
            } else {
                Snackbar.make(binding.getRoot(), R.string.no_results, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBuscarAlimentoFrag(String alimento, View v) {
        //Poner el listado a null
        DatosAlimentos.getInstance().getAlimentos().clear();
        // Get the search string from the input field.
        String queryString = alimento;

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
            Snackbar.make(binding.getRoot(), R.string.cargando, Snackbar.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            FatSecretSearch fatSecretSearch = new FatSecretSearch();
            fatSecretSearch.searchFood(queryString, 1);

            FatSecretGet fatSecretGet = new FatSecretGet();
            fatSecretGet.getFood(queryString);
//            Runable runnable = new Runable(DietaActivity.this, queryString);
//            new Thread(runnable).start();
        } else {
            if (queryString.length() == 0) {
//                    Snackbar.make(findViewById(R.id.layCoordinator), R.string.no_search_term, Snackbar.LENGTH_SHORT).show();
            } else {
//                    Snackbar.make(findViewById(R.id.layCoordinator), R.string.no_network, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFinalizarSeleccionFrag(Alimento a, Cliente c) {

    }

    @Override
    public void onSeleccionarAlimentoFrag(Alimento a) {

    }
}