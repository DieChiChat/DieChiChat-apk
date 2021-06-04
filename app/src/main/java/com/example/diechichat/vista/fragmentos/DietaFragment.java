package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diechichat.databinding.FragmentDietaBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.FiltroAlimentos;
import com.example.diechichat.vista.adaptadores.AdaptadorDieta;
import com.example.diechichat.vistamodelo.ClienteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DietaFragment extends Fragment {

    private FragmentDietaBinding binding;
    private DietaFragment.DietaFragmentInterface mListener;
    private ClienteViewModel cliVM;

    private AdaptadorDieta mAdaptadorDesayuno;
    private AdaptadorDieta mAdaptadorComida;
    private AdaptadorDieta mAdaptadorCena;
    private AdaptadorDieta mAdaptadorOtros;

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

        mAdaptadorDesayuno = new AdaptadorDieta();
        mAdaptadorComida = new AdaptadorDieta();
        mAdaptadorCena = new AdaptadorDieta();
        mAdaptadorOtros = new AdaptadorDieta();

        // Inits Observer Dieta Desayuno
        FiltroAlimentos filtroDesayuno = new FiltroAlimentos(OP_DESAYUNO, cliVM.getLogin());
        cliVM.getAlimentosME(filtroDesayuno).observe(this, new Observer<List<Alimento>>() {
            @Override
            public void onChanged(List<Alimento> alimentos) {
                mAdaptadorDesayuno.setDatos(alimentos);
                mAdaptadorDesayuno.notifyDataSetChanged();
                if (mAdaptadorDesayuno.getItemPos() != -1 &&
                        mAdaptadorDesayuno.getItemPos() < mAdaptadorDesayuno.getItemCount()) {
                    binding.rvDesayuno.scrollToPosition(mAdaptadorDesayuno.getItemPos());
                } else if (mAdaptadorDesayuno.getItemCount() > 0) {
                    binding.rvDesayuno.scrollToPosition(mAdaptadorDesayuno.getItemCount() - 1);
                }
                mAdaptadorDesayuno.setItemPos(-1);
                mAdaptadorDesayuno.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Comida

        FiltroAlimentos filtroComida = new FiltroAlimentos(OP_COMIDA, cliVM.getLogin());
        cliVM.getAlimentosME(filtroComida).observe(this, new Observer<List<Alimento>>() {
            @Override
            public void onChanged(List<Alimento> alimentos) {
                mAdaptadorComida.setDatos(alimentos);
                mAdaptadorComida.notifyDataSetChanged();
                if (mAdaptadorComida.getItemPos() != -1 &&
                        mAdaptadorComida.getItemPos() < mAdaptadorComida.getItemCount()) {
                    binding.rvComida.scrollToPosition(mAdaptadorComida.getItemPos());
                } else if (mAdaptadorComida.getItemCount() > 0) {
                    binding.rvComida.scrollToPosition(mAdaptadorComida.getItemCount() - 1);
                }
                mAdaptadorComida.setItemPos(-1);
                mAdaptadorComida.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Cena
        FiltroAlimentos filtroCena = new FiltroAlimentos(OP_CENA, cliVM.getLogin());
        cliVM.getAlimentosME(filtroCena).observe(this, new Observer<List<Alimento>>() {
            @Override
            public void onChanged(List<Alimento> alimentos) {
                mAdaptadorCena.setDatos(alimentos);
                mAdaptadorCena.notifyDataSetChanged();
                if (mAdaptadorCena.getItemPos() != -1 &&
                        mAdaptadorCena.getItemPos() < mAdaptadorCena.getItemCount()) {
                    binding.rvCena.scrollToPosition(mAdaptadorCena.getItemPos());
                } else if (mAdaptadorCena.getItemCount() > 0) {
                    binding.rvCena.scrollToPosition(mAdaptadorCena.getItemCount() - 1);
                }
                mAdaptadorCena.setItemPos(-1);
                mAdaptadorCena.notifyDataSetChanged();
            }
        });

        // Inits Observer Dieta Otros
        FiltroAlimentos filtroOtros = new FiltroAlimentos(OP_OTROS, cliVM.getLogin());
        cliVM.getAlimentosME(filtroOtros).observe(this, new Observer<List<Alimento>>() {
            @Override
            public void onChanged(List<Alimento> alimentos) {
                mAdaptadorOtros.setDatos(alimentos);
                mAdaptadorOtros.notifyDataSetChanged();
                if (mAdaptadorOtros.getItemPos() != -1 &&
                        mAdaptadorOtros.getItemPos() < mAdaptadorOtros.getItemCount()) {
                    binding.rvOtros.scrollToPosition(mAdaptadorOtros.getItemPos());
                } else if (mAdaptadorOtros.getItemCount() > 0) {
                    binding.rvOtros.scrollToPosition(mAdaptadorOtros.getItemCount() - 1);
                }
                mAdaptadorOtros.setItemPos(-1);
                mAdaptadorOtros.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDietaBinding.inflate(inflater, container, false);

        if (cliVM.getEsCliente()) {
            binding.btAddDesayuno.setVisibility(View.INVISIBLE);
            binding.btAddComida.setVisibility(View.INVISIBLE);
            binding.btAddCena.setVisibility(View.INVISIBLE);
            binding.btAddOtros.setVisibility(View.INVISIBLE);
        } else {
            binding.btAddDesayuno.setVisibility(View.VISIBLE);
            binding.btAddComida.setVisibility(View.VISIBLE);
            binding.btAddCena.setVisibility(View.VISIBLE);
            binding.btAddOtros.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init adaptador desayuno
        binding.rvDesayuno.setHasFixedSize(true);
        binding.rvDesayuno.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvDesayuno.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvDesayuno.setAdapter(mAdaptadorDesayuno);

        //init adaptador comida
        binding.rvComida.setHasFixedSize(true);
        binding.rvComida.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvComida.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvComida.setAdapter(mAdaptadorComida);

        //init adaptador cena
        binding.rvCena.setHasFixedSize(true);
        binding.rvCena.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvCena.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvCena.setAdapter(mAdaptadorCena);

        //init adaptador otros
        binding.rvOtros.setHasFixedSize(true);
        binding.rvOtros.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvOtros.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvOtros.setAdapter(mAdaptadorOtros);

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
            if (v == binding.btAddDesayuno) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_DESAYUNO);
            } else if (v == binding.btAddComida) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_COMIDA);
            } else if (v == binding.btAddCena) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_CENA);
            } else if (v == binding.btAddOtros) {
                mListener.onAsignarAlimento(cliVM.getLogin(), OP_OTROS);
            }
        }
    };


}