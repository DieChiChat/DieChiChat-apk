package com.example.diechichat.vista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.modelo.Runable;
import com.example.diechichat.vista.fragmentos.AlimentoSeleccionadoFragment;
import com.example.diechichat.vista.fragmentos.AlimentosFragment;
import com.example.diechichat.vista.fragmentos.DietaFragment;
import com.example.diechichat.vistamodelo.AlimentoViewModel;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.databinding.ActivityDietaBinding;

import com.example.diechichat.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DietaActivity extends AppCompatActivity implements
        DietaFragment.DietaFragmentInterface,
        AlimentosFragment.AlimentoFragmentInterface,
        AlimentoSeleccionadoFragment.AlimentoSeleccionadoFragmentInterface {

    private ActivityDietaBinding binding;
    private NavController mNavC;
    private ClienteViewModel cliVM;
    private AlimentoViewModel alimentoVM;
    private int opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cliVM = new ViewModelProvider(this).get(ClienteViewModel.class);
        alimentoVM = new ViewModelProvider(this).get(AlimentoViewModel.class);

        Intent i = getIntent();
        if(i != null) {
            cliVM.setLogin(i.getParcelableExtra("cliente"));
        }

        DietaActivity.Receptor receptor = new DietaActivity.Receptor();
        IntentFilter filter = new IntentFilter();
        filter.addAction("RESPUESTA_JSON");
        LocalBroadcastManager.getInstance(this).registerReceiver(receptor, filter);

        binding = ActivityDietaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.dietaFragCV)).getNavController();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mNavC.navigateUp();
                //onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Métodos DietaFragment **************************************************/
    @Override
    public void onAsignarAlimento(Cliente c, int opcion) {
        if(c != null) {
            Bundle bundleCli= new Bundle();
            bundleCli.putParcelable("cliente", c);
            bundleCli.putInt("opcion", opcion);
            mNavC.navigate(R.id.action_fragment_dieta_to_fragment_alimentos, bundleCli);
        }
    }


    /** Métodos AlimentosFragment **************************************************/

    @Override
    public void onFinalizarSeleccionFrag() {
        mNavC.navigateUp();
    }

    @Override
    public void onSeleccionarAlimentoFrag(Alimento alimento, Cliente cliente, int op) {
        if(alimento != null && cliente != null && op != -1) {
            Bundle b = new Bundle();
            b.putParcelable("alimento", alimento);
            b.putParcelable("cliente", cliente);
            b.putInt("opcion", op);
            mNavC.navigate(R.id.action_fragment_alimentos_to_fragment_alimento_seleccionado, b);
        }
    }

    /**Métodos búsqueda de alimentos en API*************************************/

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
        if(!alimento.equals("") && v != null) {
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
                Runable runnable = new Runable(DietaActivity.this, queryString);
                new Thread(runnable).start();

            } else {
                if (queryString.length() == 0) {
                    Snackbar.make(binding.getRoot(), R.string.no_results, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(binding.getRoot(), R.string.no_network, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }


    /** Métodos AlimentoSeleccionadoFragment ***************************************/

    @Override
    public void onAceptarAliSeleccionadoFrag(Cliente c) {
        if(c != null) {
            cliVM.editarCliente(c).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                    Toast.makeText(getApplication(), (ok) ? R.string.msg_alimentoGuardado : R.string.msg_alimentoNoGuardado, Toast.LENGTH_SHORT).show();
                }
            });
            mNavC.navigateUp();
        }
    }

    @Override
    public void onCancelarAliSeleccionadoFrag() {

    }

}