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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityAlimentosBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.modelo.Runable;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vista.fragmentos.AlimentosFragment;
import com.example.diechichat.vistamodelo.AlimentoViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class AlimentosActivity extends AppCompatActivity implements
        AlimentosFragment.AlimentoFragmentInterface {

    private ActivityAlimentosBinding binding;
    private NavController mNavC;
    private AlimentoViewModel alimentoVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlimentosBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_alimentos);

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        alimentoVM = new ViewModelProvider(this).get(AlimentoViewModel.class);

        Receptor receptor = new Receptor();
        IntentFilter filter = new IntentFilter();
        filter.addAction("RESPUESTA_JSON");
        LocalBroadcastManager.getInstance(this).registerReceiver(receptor, filter);

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.alimentosFragCV)).getNavController();
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
//            Snackbar.make(findViewById(R.id.layCoordinator), R.string.loading, Snackbar.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            Runable runnable = new Runable(AlimentosActivity.this, queryString);
            new Thread(runnable).start();
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