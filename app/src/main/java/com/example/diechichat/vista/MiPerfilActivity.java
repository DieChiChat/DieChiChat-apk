package com.example.diechichat.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.fragmentos.MiPerfilFragment;
import com.example.diechichat.vistamodelo.MainViewModel;
import com.example.diechichat.vistamodelo.NutriViewModel;

public class MiPerfilActivity extends AppCompatActivity implements MiPerfilFragment.PerfilFragInterface {

    private ActivityMiPerfilBinding binding;
    private NavController mNavC;
    private MainViewModel mainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMiPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mainVM = new ViewModelProvider(this).get(MainViewModel.class);

        Intent i = getIntent();
        Bundle b = new Bundle();
        if(i != null) {
            mainVM.setLogin(i.getExtras().getParcelable("nutricionista"));
            b.putParcelable("nutricionista", (Nutricionista)mainVM.getLogin());
        }
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.miperfilFragCV)).getNavController();
//        if(b != null) {
//            mNavC.navigate(R.id.dieta_nav_graph, b);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAceptarPerfilFrag() {

    }
    @Override
    public void onCancelarPerfilFrag() {
        finish();
    }
}
