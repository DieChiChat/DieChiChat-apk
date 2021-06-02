package com.example.diechichat.vista.fragmentos;

import android.content.Context;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ContentRvAlimentosBinding;
import com.example.diechichat.databinding.FragmentAlimentosBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.DatosAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vistamodelo.AlimentoViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlimentosFragment extends Fragment {

    private AdaptadorAlimentos mAdapterAlimentos;
    private FragmentAlimentosBinding binding;
    private AlimentoViewModel alimentoVM;
    private ContentRvAlimentosBinding bindingAlimentos;

    private int mOp;
    private Cliente cli;
    private Alimento alimento;

    private AlimentoFragmentInterface mListener;

    public AlimentosFragment() {
        // Required empty public constructor
    }

    public interface AlimentoFragmentInterface {
        void onBuscarAlimentoFrag(String alimento, View v);

        void onFinalizarSeleccionFrag();

        void onSeleccionarAlimentoFrag(Alimento alimento, Cliente cliente, int op);
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
            cli = getArguments().getParcelable("cliente");
        }
        setHasOptionsMenu(true);

        alimentoVM = new ViewModelProvider(requireActivity()).get(AlimentoViewModel.class);

        mAdapterAlimentos = new AdaptadorAlimentos();

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
        bindingAlimentos = ContentRvAlimentosBinding.inflate(inflater, container, false);
        bindingAlimentos.tvNombreAlimento.setText("");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvAlimentos.setHasFixedSize(true);
        binding.rvAlimentos.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvAlimentos.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvAlimentos.setAdapter(mAdapterAlimentos);
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
                if (mListener != null) {
                    if (item.getItemId() == R.id.menuOk) {
                        if (!bindingAlimentos.tvNombreAlimento.getText().toString().equals("")) {
                            String sfasdf = bindingAlimentos.tvNombreAlimento.getText().toString();
                            alimento = new Alimento();
                            alimento = mAdapterAlimentos.getItem(posRecicler);
                            mListener.onSeleccionarAlimentoFrag(alimento, cli, mOp);
                        } else {
                            mListener.onBuscarAlimentoFrag("", null);
                        }
                    } else if (item.getItemId() == R.id.menuFinalizar) {
                        mListener.onFinalizarSeleccionFrag();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /***************************************/
    View.OnClickListener btBuscarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatosAlimentos.getInstance().getAlimentos().clear();
            String query = "";
            String palabraBusqueda = binding.etAlimento.getText().toString();
            for (int i = 0; i < palabraBusqueda.length(); i++) {
                char letra = palabraBusqueda.charAt(i);
                if (String.valueOf(letra).equals(" ")) {
                    query = query + "%20";
                } else {
                    query = query + letra;
                }
            }
            mListener.onBuscarAlimentoFrag(query, v);
            mAdapterAlimentos.notifyDataSetChanged();
        }
    };
}