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
    private MisClientesFragInterface mListener;

    public interface MisClientesFragInterface {
        void onEditarBusIncsFrag(Cliente cli);
        void onAsignarDietaBusClisFrag();
        void onEliminarBusClisFrag(Cliente cli);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MisClientesFragInterface) {
            mListener = (MisClientesFragInterface) context;
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
//                binding.btIncEliminar.setEnabled(false);
//                binding.btIncEditar.setEnabled(false);
//                binding.btIncCrear.setEnabled(true);
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
        // Init RecyclerView Dptos
        binding.rvClientes.setHasFixedSize(true);
        binding.rvClientes.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvClientes.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvClientes.setAdapter(mAdaptadorClis);

        // Inits
        if (mAdaptadorClis.getItemPos() != -1) {
            binding.btEditar.setEnabled(true);
            binding.btAsignarDieta.setEnabled(true);
            binding.btEliminar.setEnabled(true);
        } else {
//            binding.btEditar.setEnabled(false);
//            binding.btAsignarDieta.setEnabled(false);
//            binding.btEliminar.setEnabled(false);
            binding.btEditar.setEnabled(true);
            binding.btAsignarDieta.setEnabled(true);
            binding.btEliminar.setEnabled(true);
        }

        // Listeners0
        binding.btEditar.setOnClickListener(btEditarCli_OnClickListener);
        binding.btAsignarDieta.setOnClickListener(btAsignarDieta_OnClickListener);
        binding.btEliminar.setOnClickListener(btIncEliminar_OnClickListener);
        mAdaptadorClis.setOnClickListener(mAdaptadorClis_OnClickListener);

//        TODO: ya no es necesario refrescar, se refresca automáticamente
//        binding.srIncs.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Inits Incs sin Observer
//                IncsViewModel incVM = new ViewModelProvider(requireActivity()).get(IncsViewModel.class);
//                incVM.getIncs();
//                binding.srIncs.setRefreshing(false);
//            }
//        });
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
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_filtro, menu);
    }

    private final View.OnClickListener mAdaptadorClis_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorClis.getItemPos();
            if (pos != -1) {
                binding.btEditar.setEnabled(true);
                binding.btAsignarDieta.setEnabled(true);
                binding.btEliminar.setEnabled(false);
            } else {
                binding.btEditar.setEnabled(false);
                binding.btAsignarDieta.setEnabled(false);
                binding.btEliminar.setEnabled(false);
            }
        }
    };

    private final View.OnClickListener btAsignarDieta_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorClis.getItemPos();
            if (pos == -1) {
                if (mListener != null) {
                    mListener.onAsignarDietaBusClisFrag();
                }
            }
        }
    };

    private final View.OnClickListener btEditarCli_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorClis.getItemPos();
            if (pos >= 0) {
                if (mListener != null) {
                    mListener.onEditarBusIncsFrag(mAdaptadorClis.getItem(pos));
                }
            }
        }
    };

    private final View.OnClickListener btIncEliminar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = mAdaptadorClis.getItemPos();
            if (pos >= 0) {
                if (mListener != null) {
                    mListener.onEliminarBusClisFrag(mAdaptadorClis.getItem(pos));
                }
            }
        }
    };
}