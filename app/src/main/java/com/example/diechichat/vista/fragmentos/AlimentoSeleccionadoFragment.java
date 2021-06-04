package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentAlimentoSeleccionadoBinding;
import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;

import org.jetbrains.annotations.NotNull;

public class AlimentoSeleccionadoFragment extends Fragment {

    private FragmentAlimentoSeleccionadoBinding binding;
    private AlimentoSeleccionadoFragmentInterface mListener;

    private Cliente cliente;
    private Alimento alimento;
    private int mOp;

    public AlimentoSeleccionadoFragment() {
        // Required empty public constructor
    }

    public interface AlimentoSeleccionadoFragmentInterface {
        void onAceptarAliSeleccionadoFrag(Cliente c);

        void onCancelarAliSeleccionadoFrag();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AlimentoSeleccionadoFragment.AlimentoSeleccionadoFragmentInterface) {
            mListener = (AlimentoSeleccionadoFragment.AlimentoSeleccionadoFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AlimentoSeleccionadoFragmentInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            alimento = b.getParcelable("alimento");
            cliente = b.getParcelable("cliente");
            mOp = b.getInt("opcion");
        }

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlimentoSeleccionadoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (alimento != null) {
            binding.etNombreAlimento.setText(alimento.getNombre());
            binding.etGrasa.setText(String.valueOf(alimento.getGrasa()));
            binding.etFibra.setText(String.valueOf(alimento.getFibra()));
            binding.etCarbohidratos.setText(String.valueOf(alimento.getCarbohidratos()));
            binding.etKcal.setText(String.valueOf(alimento.getKcal()));
            binding.etProteinas.setText(String.valueOf(alimento.getProteinas()));
        }

        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(view.getContext(), R.array.array_tipos, android.R.layout.simple_spinner_item);
        binding.spTipos.setAdapter(adapterS);

        binding.btAceptarAlimentos.setOnClickListener(btAceptarAlimento_onClickListener);
        binding.btCancelarAlimentos.setOnClickListener(btCancelarAlimento_onClickListener);
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


    /***************************************/
    View.OnClickListener btAceptarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!binding.etCantidad.getText().toString().equals("") && !binding.spTipos.getSelectedItem().toString().equals("")) {
                Alimento a = new Alimento();
                a.setId(1);
                a.setNombre(binding.etNombreAlimento.getText().toString());
                a.setProteinas(Double.parseDouble(binding.etProteinas.getText().toString()));
                a.setKcal(Double.parseDouble(binding.etKcal.getText().toString()));
                a.setCarbohidratos(Double.parseDouble(binding.etCarbohidratos.getText().toString()));
                a.setFibra(Double.parseDouble(binding.etFibra.getText().toString()));
                a.setGrasa(Double.parseDouble(binding.etGrasa.getText().toString()));
                String cantidad = binding.etCantidad.getText().toString() + " " + binding.spTipos.getSelectedItem().toString();
                a.setCantidad(cantidad);
                switch (mOp) {
                    case DietaFragment.OP_DESAYUNO:
                        cliente.getDesayuno().add(a);
                        break;
                    case DietaFragment.OP_COMIDA:
                        cliente.getComida().add(a);
                        break;
                    case DietaFragment.OP_CENA:
                        cliente.getCena().add(a);
                        break;
                    case DietaFragment.OP_OTROS:
                        cliente.getOtros().add(a);
                        break;
                }
                mListener.onAceptarAliSeleccionadoFrag(cliente);
            } else {
                mListener.onAceptarAliSeleccionadoFrag(null);
            }
        }
    };


    View.OnClickListener btCancelarAlimento_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onCancelarAliSeleccionadoFrag();
        }
    };

}