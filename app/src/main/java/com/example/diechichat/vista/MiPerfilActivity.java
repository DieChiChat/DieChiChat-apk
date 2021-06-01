package com.example.diechichat.vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.fragmentos.MiPerfilFragment;
import com.example.diechichat.vistamodelo.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MiPerfilActivity extends AppCompatActivity implements MiPerfilFragment.PerfilFragInterface {

    private ActivityMiPerfilBinding binding;
    private NavController mNavC;
    private MainViewModel mainVM;
    private Nutricionista nutricionista;
//    public static Nutricionista nutricionista;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mainVM = new ViewModelProvider(this).get(MainViewModel.class);

        Intent i = getIntent();
        if (i != null) {
            Bundle b = i.getExtras();
            if (b != null) {
                nutricionista = (Nutricionista) b.getParcelable("login");
                mainVM.setLogin(nutricionista);
            }
        }

        binding = ActivityMiPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.miperfilFragCV)).getNavController();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nutricionista = null;
    }

    @Override
    public void onAceptarPerfilFrag(Nutricionista nutricionista) {
        if (nutricionista != null) {
            mainVM.editarNutricionista(nutricionista).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                    Toast.makeText(getApplication(), (ok) ? R.string.msg_editarOk : R.string.msg_editarKo, Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        } else {
            Snackbar.make(binding.getRoot(), R.string.msg_datosObligatorios, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelarPerfilFrag() {
        finish();
    }
}
