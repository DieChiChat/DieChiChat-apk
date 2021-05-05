package com.example.diechichat;

import android.os.Bundle;

import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.vista.fragmentos.MiPerfilFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.MenuItem;
import android.view.View;

public class MiPerfilActivity extends AppCompatActivity implements MiPerfilFragment.PerfilFragInterface {

    private ActivityMiPerfilBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMiPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavC=((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.miperfilFragCV)).getNavController();
//        setContentView(R.layout.activity_mi_perfil);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_miperfil){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAceptarPerfilFrag() {

    }

    @Override
    public void onCancelarPerfilFrag() {
        finish();
    }
}