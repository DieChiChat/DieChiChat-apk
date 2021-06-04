package com.example.diechichat.vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.dialogos.DlgAlerta;
import com.example.diechichat.vista.fragmentos.MiPerfilFragment;
import com.example.diechichat.vistamodelo.NutriViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MiPerfilActivity extends AppCompatActivity implements MiPerfilFragment.PerfilFragInterface {

    private static final String TAG_ALERTA = "tagAlerta";

    private ActivityMiPerfilBinding binding;
    private NavController mNavC;
    private NutriViewModel nutriVM;
    private Nutricionista nutricionista;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        nutriVM = new ViewModelProvider(this).get(NutriViewModel.class);

        Intent i = getIntent();
        if (i != null) {
            Bundle b = i.getExtras();
            if (b != null) {
                nutricionista = (Nutricionista) b.getParcelable("login");
                nutriVM.setLogin(nutricionista);
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
            nutriVM.editarNutricionista(nutricionista).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean ok) {
                    //Toast.makeText(getApplication(), (ok) ? R.string.msg_editarOk : R.string.msg_editarKo, Toast.LENGTH_SHORT).show();
                    if(ok) {
                        mensajeInformacion(R.string.titleInformacion, R.string.msgInformacion);
                    }
                }
            });
        } else {
            Snackbar.make(binding.getRoot(), R.string.msg_datosObligatorios, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelarPerfilFrag() {
        finish();
    }

    public void mensajeInformacion(int titulo, int mensaje) {
        DlgAlerta da = new DlgAlerta();
        da.setTitulo(titulo);
        da.setMensaje(mensaje);
        da.show(getSupportFragmentManager(), TAG_ALERTA);
    }
}
