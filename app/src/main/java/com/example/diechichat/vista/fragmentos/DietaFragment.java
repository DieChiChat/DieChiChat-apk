package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diechichat.databinding.FragmentDietaBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.adaptadores.AdaptadorAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorClientes;
import com.example.diechichat.vista.adaptadores.AdaptadorDieta;
import com.example.diechichat.vistamodelo.AlimentoViewModel;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DietaFragment extends Fragment {

    private FragmentDietaBinding binding;
    private DietaFragment.DietaFragmentInterface mListener;
    private Cliente cliUsuario;
    private Nutricionista nutriUsuario;
    private ClienteViewModel cliVM;

    private AdaptadorDieta mAdaptadorDieta;

    public static final int OP_DESAYUNO = 0;
    public static final int OP_COMIDA = 1;
    public static final int OP_CENA = 2;
    public static final int OP_OTROS = 3;

    public DietaFragment() {
        // Required empty public constructor
    }


    public interface DietaFragmentInterface {
        void onAsignarAlimento(Cliente c, int opcion);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DietaFragment.DietaFragmentInterface) {
            mListener = (DietaFragment.DietaFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DietaFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        cliVM = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);

        mAdaptadorDieta = new AdaptadorDieta();
        // Inits Observer Dieta Desayuno
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorDieta.setDatos(clis);
                mAdaptadorDieta.notifyDataSetChanged();
                if (mAdaptadorDieta.getItemPos() != -1 &&
                        mAdaptadorDieta.getItemPos() < mAdaptadorDieta.getItemCount()) {
                    binding.rvDesayuno.scrollToPosition(mAdaptadorDieta.getItemPos());
                } else if (mAdaptadorDieta.getItemCount() > 0) {
                    binding.rvDesayuno.scrollToPosition(mAdaptadorDieta.getItemCount() - 1);
                }
                mAdaptadorDieta.setItemPos(-1);
                mAdaptadorDieta.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Comida
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorDieta.setDatos(clis);
                mAdaptadorDieta.notifyDataSetChanged();
                if (mAdaptadorDieta.getItemPos() != -1 &&
                        mAdaptadorDieta.getItemPos() < mAdaptadorDieta.getItemCount()) {
                    binding.rvComida.scrollToPosition(mAdaptadorDieta.getItemPos());
                } else if (mAdaptadorDieta.getItemCount() > 0) {
                    binding.rvComida.scrollToPosition(mAdaptadorDieta.getItemCount() - 1);
                }
                mAdaptadorDieta.setItemPos(-1);
                mAdaptadorDieta.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Cena
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorDieta.setDatos(clis);
                mAdaptadorDieta.notifyDataSetChanged();
                if (mAdaptadorDieta.getItemPos() != -1 &&
                        mAdaptadorDieta.getItemPos() < mAdaptadorDieta.getItemCount()) {
                    binding.rvCena.scrollToPosition(mAdaptadorDieta.getItemPos());
                } else if (mAdaptadorDieta.getItemCount() > 0) {
                    binding.rvCena.scrollToPosition(mAdaptadorDieta.getItemCount() - 1);
                }
                mAdaptadorDieta.setItemPos(-1);
                mAdaptadorDieta.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Otros
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                mAdaptadorDieta.setDatos(clis);
                mAdaptadorDieta.notifyDataSetChanged();
                if (mAdaptadorDieta.getItemPos() != -1 &&
                        mAdaptadorDieta.getItemPos() < mAdaptadorDieta.getItemCount()) {
                    binding.rvOtros.scrollToPosition(mAdaptadorDieta.getItemPos());
                } else if (mAdaptadorDieta.getItemCount() > 0) {
                    binding.rvOtros.scrollToPosition(mAdaptadorDieta.getItemCount() - 1);
                }
                mAdaptadorDieta.setItemPos(-1);
                mAdaptadorDieta.notifyDataSetChanged();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDietaBinding.inflate(inflater, container, false);

        if(nutriUsuario != null) {
            binding.btAddDesayuno.setVisibility(View.INVISIBLE);
            binding.btAddComida.setVisibility(View.INVISIBLE);
            binding.btAddCena.setVisibility(View.INVISIBLE);
            binding.btAddOtros.setVisibility(View.INVISIBLE);
        }

        if(cliUsuario != null) {
            binding.btAddDesayuno.setVisibility(View.INVISIBLE);
            binding.btAddComida.setVisibility(View.INVISIBLE);
            binding.btAddCena.setVisibility(View.INVISIBLE);
            binding.btAddOtros.setVisibility(View.INVISIBLE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btAddDesayuno.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddComida.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddCena.setOnClickListener(btAddDieta_onClickListener);
        binding.btAddOtros.setOnClickListener(btAddDieta_onClickListener);
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

    View.OnClickListener btAddDieta_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == binding.btAddDesayuno) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_DESAYUNO);
            } else if(v == binding.btAddComida) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_COMIDA);
            } else if(v == binding.btAddCena) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_CENA);
            } else if(v == binding.btAddOtros) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_OTROS);
            }
        }
    };


}