package com.example.diechichat.vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.diechichat.R;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.fragmentos.MisClientesFragment;

public class MisClientesActivity extends AppCompatActivity
        implements MisClientesFragment.MisClientesFragInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clientes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onEditarBusIncsFrag(Cliente cli) {

    }

    @Override
    public void onAsignarDietaBusClisFrag() {

    }

    @Override
    public void onEliminarBusClisFrag(Cliente cli) {

    }
}
