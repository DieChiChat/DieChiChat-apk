package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentAlimentosBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vistamodelo.AlimentoViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlimentosFragment extends Fragment {

    private AdaptadorAlimentos mAdapterAlimentos;
    private FragmentAlimentosBinding binding;
    private AlimentoViewModel alimentoVM;

    private int mOp;
    private Cliente cli;
    private List<Alimento> aliSeleccionados;

    private AlimentoFragmentInterface mListener;

    public AlimentosFragment() {
        // Required empty public constructor
    }

    public interface AlimentoFragmentInterface {
        void onBuscarAlimentoFrag(String alimento, View v);
        void onFinalizarSeleccionFrag(Alimento a, Cliente c);
        void onSeleccionarAlimentoFrag(Alimento a);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AlimentosFragment.AlimentoFragmentInterface) {
            mListener = (AlimentosFragment.AlimentoFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AlimentoFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOp = getArguments().getInt("opcion");
            cli = getArguments().getParcelable("clienteAddDieta");
        }
        setHasOptionsMenu(true);

        alimentoVM = new ViewModelProvider(this).get(AlimentoViewModel.class);
        aliSeleccionados = new ArrayList<>();

        mAdapterAlimentos = new AdaptadorAlimentos();
        mAdapterAlimentos.setDatos(DatosAlimentos.getInstance().getAlimentos());

        alimentoVM.getListado().observe(this, new Observer<List<Alimento>>() {
            @Override
            public void onChanged(List<Alimento> alimentos) {
                mAdapterAlimentos.setDatos(alimentos);
                mAdapterAlimentos.notifyDataSetChanged();
                if (mAdapterAlimentos.getItemPos() != -1 &&
                        mAdapterAlimentos.getItemPos() < mAdapterAlimentos.getItemCount()) {
                    binding.rvAlimentos.scrollToPosition(mAdapterAlimentos.getItemPos());
                } else if (mAdapterAlimentos.getItemCount() > 0) {
                    binding.rvAlimentos.scrollToPosition(mAdapterAlimentos.getItemCount() - 1);
                }
                mAdapterAlimentos.setItemPos(-1);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlimentosBinding.inflate(inflater, container, false);

        binding.rvAlimentos.setAdapter(mAdapterAlimentos);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btBuscarAlimento.setOnClickListener(btBuscarAlimento_onClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_alimentos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int posRecicler = mAdapterAlimentos.getItemPos();
        if (posRecicler >= 0) {
            if (mListener != null) {
                if (item.getItemId() == R.id.menuOk) {
                        mListener.onSeleccionarAlimentoFrag(mAdapterAlimentos.getItem(posRecicler));
                } else if (item.getItemId() == R.id.menuFinalizar) {
                    if (mListener != null) {
                        mListener.onFinalizarSeleccionFrag(mAdapterAlimentos.getItem(posRecicler), cli);
                    }
                }
            }
        }
        mListener.onSeleccionarAlimentoFrag(null);
        return super.onOptionsItemSelected(item);
    }

    /***************************************/
    View.OnClickListener btBuscarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String query = "";
            String palabraBusqueda = binding.etAlimento.getText().toString();
            for (int i = 0; i < palabraBusqueda.length(); i++) {
                char letra = palabraBusqueda.charAt(i);
                if(String.valueOf(letra).equals(" ")) {
                    query = query + "%20";
                } else {
                    query = query + letra;
                }
            }
            mListener.onBuscarAlimentoFrag(query, v);
            List<Alimento> tAlimentos = DatosAlimentos.getInstance().getAlimentos();
            mAdapterAlimentos.notifyDataSetChanged();
        }
    };

}