package com.example.diechichat.vista.fragmentos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.R;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.vista.DietaActivity;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;

public class AlimentosFragment extends Fragment {

    private RecyclerView rvAlimentos;
    private AdaptadorAlimentos mAdapterAlimentos;

    public AlimentosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mAdapterAlimentos = new AdaptadorAlimentos();
        mAdapterAlimentos.setDatos(DatosAlimentos.getInstance().getAlimentos());
        rvAlimentos.setAdapter(mAdapterAlimentos);
        Receptor receptor = new Receptor();
        IntentFilter filter = new IntentFilter();
        filter.addAction("RESPUESTA_JSON");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receptor, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alimentos, container, false);
    }


    public class Receptor extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("RESPUESTA_JSON") && intent.getParcelableArrayListExtra("json") != null) {
                //ArrayList<Libro> tLibros = intent.getParcelableArrayListExtra("json");
                mAdapterAlimentos.notifyDataSetChanged();
            } else {
//                Snackbar.make(findViewById(R.id.layCoordinator), R.string.no_results, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}