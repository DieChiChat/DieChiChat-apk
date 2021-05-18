package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMiPerfilBinding;
import com.example.diechichat.databinding.ActivityMisClientesBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vista.fragmentos.MisClientesFragment;
import com.google.android.material.snackbar.Snackbar;

public class MisClientesActivity extends AppCompatActivity
        implements AdaptadorClientes.AdaptadorClientesInterface {
    private ActivityMisClientesBinding binding;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisClientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.misclientesFragCV)).getNavController();
//        setContentView(R.layout.activity_mi_perfil);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    public void onClickImagebVer() {
        // Lanzamos MtoDptosFragment
        Bundle bundle = new Bundle();
//        bundle.putInt("op", NuevoClienteActivity.OP_CREAR);
//        bundle.putParcelable("cli", cli);
//        bundle.putString("nombre", cli.getNombre());
//        bundle.putString("apellidos", cli.getApellidos());
//        bundle.putString("usuario", cli.getUsuario());
//        bundle.putString("contrasena", cli.getContrasena());
//        bundle.putInt("peso", cli.getPeso());
//        bundle.putInt("altura", cli.getAltura());
       // bundle.putParcelable(); --> Aquí hay que meterle el cliente para pasárselo al fragmento y rellenarlo :)
        mNavC.navigate(R.id.action_mis_lientes_to_nuevoCienteFragment);
    }

    @Override
    public void onClickIgmagebAdd() {
        Snackbar.make(binding.getRoot(), "añadir dieta", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClickImagebCamara() {

    }
}
