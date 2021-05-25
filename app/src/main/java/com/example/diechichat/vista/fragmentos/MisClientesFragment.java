package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentMisClientesBinding;
import com.example.diechichat.databinding.FragmentNuevoClienteBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import java.util.List;

public class MisClientesFragment extends Fragment {
    private AdaptadorClientes mAdaptadorClis;
    private FragmentMisClientesBinding binding;
    private MisClientesFragmentInterface mListener;

    public interface MisClientesFragmentInterface {
        void onVerClienteFrag(Cliente cli);

        void onAddDietaFrag(Cliente cli);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MisClientesFragmentInterface) {
            mListener = (MisClientesFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MisClientesFragInterface");
        }
    }

    public MisClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }

        // Inits
        ClienteViewModel clisVM = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        mAdaptadorClis = new AdaptadorClientes();

        // Inits Incs Observer
        clisVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorClis.setDatos(clis);
                mAdaptadorClis.notifyDataSetChanged();
                if (mAdaptadorClis.getItemPos() != -1 &&
                        mAdaptadorClis.getItemPos() < mAdaptadorClis.getItemCount()) {
                    binding.rvClientes.scrollToPosition(mAdaptadorClis.getItemPos());
                } else if (mAdaptadorClis.getItemCount() > 0) {
                    binding.rvClientes.scrollToPosition(mAdaptadorClis.getItemCount() - 1);
                }
                mAdaptadorClis.setItemPos(-1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMisClientesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Init RecyclerView Clientes
        binding.rvClientes.setHasFixedSize(true);
        binding.rvClientes.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvClientes.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvClientes.setAdapter(mAdaptadorClis);

        // Listeners0
        mAdaptadorClis.setOnClickListener(mAdaptadorClis_OnClickListener);
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
        inflater.inflate(R.menu.menu_mis_clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuVerCliente) {
            int posRecicler = mAdaptadorClis.getItemPos();
            if (posRecicler >= 0) {
                if (mListener != null) {
                    mListener.onVerClienteFrag(mAdaptadorClis.getItem(posRecicler));
                }
            }
        }
        if (item.getItemId() == R.id.menuAddDieta) {
            int posRecicler = mAdaptadorClis.getItemPos();
            if (posRecicler >= 0) {
                if (mListener != null) {
                    mListener.onAddDietaFrag(mAdaptadorClis.getItem(posRecicler));
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private final View.OnClickListener mAdaptadorClis_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorClis.getItemPos();
        }
    };
}