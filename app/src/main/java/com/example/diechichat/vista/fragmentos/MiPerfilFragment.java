package com.example.diechichat.vista.fragmentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentPerfilBinding;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vistamodelo.NutriViewModel;

public class MiPerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilFragInterface mListener;

    private Nutricionista nutri;

    private NutriViewModel nutriVM;

    public interface PerfilFragInterface {
        void onAceptarPerfilFrag(Nutricionista nutricionista);
        void onCancelarPerfilFrag();
    }

    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PerfilFragInterface) {
            mListener = (PerfilFragInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PerfilFragInterface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }

        nutriVM = new ViewModelProvider(requireActivity()).get(NutriViewModel.class);
        nutri = (Nutricionista) nutriVM.getLogin();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        habilitarCampos(false);
        binding.tvIdPerfil.setVisibility(View.INVISIBLE);

        if (nutri != null) {
            binding.tvIdPerfil.setText(String.valueOf(nutri.getId()));
            binding.etNombre.setText(nutri.getNombre());
            binding.etApellidos.setText(nutri.getApellidos());
            binding.etUsuario.setText(nutri.getUsuario());
            binding.etContrasena.setText(nutri.getContrasena());
            binding.etContrasena.setTransformationMethod(new PasswordTransformationMethod());
        }
        binding.btAceptar.setOnClickListener(btAceptar_onClickListener);
        binding.btCancelar.setOnClickListener(btCancelar_onClickListener);
        binding.btVerContrasena.setOnClickListener(btVerContrasena_OnClickListener);
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
        inflater.inflate(R.menu.menu_perfil_nutri, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuEditarPerfil) {
            if (binding.etNombre.isEnabled()) {
                habilitarCampos(false);
            } else {
                habilitarCampos(true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener btAceptar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);

            if (!binding.etNombre.getText().toString().equals("") &&
                    !binding.etApellidos.getText().toString().equals("") &&
                    !binding.etUsuario.getText().toString().equals("") &&
                    !binding.etContrasena.getText().toString().equals("")) {
                Nutricionista n = new Nutricionista();
                n.setId(Integer.parseInt(binding.tvIdPerfil.getText().toString()));
                n.setNombre(binding.etNombre.getText().toString());
                n.setApellidos(binding.etApellidos.getText().toString());
                n.setUsuario(binding.etUsuario.getText().toString());
                n.setContrasena(binding.etContrasena.getText().toString());
                mListener.onAceptarPerfilFrag(n);
                habilitarCampos(false);
            } else {
                mListener.onAceptarPerfilFrag(null);
            }
        }
    };
    View.OnClickListener btCancelar_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);

            if (mListener != null) {
                mListener.onCancelarPerfilFrag();
            }
        }
    };


    View.OnClickListener btVerContrasena_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            esconderTeclado(v);
            if (binding.etContrasena.getTransformationMethod() == null) {
                binding.etContrasena.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                binding.etContrasena.setTransformationMethod(null);
            }
        }
    };


    public void esconderTeclado(View v) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void habilitarCampos(boolean habilitado) {
        if (habilitado) {
            binding.etNombre.setEnabled(true);
            binding.etApellidos.setEnabled(true);
            binding.etUsuario.setEnabled(true);
            binding.etContrasena.setEnabled(true);
        } else {
            binding.etNombre.setEnabled(false);
            binding.etApellidos.setEnabled(false);
            binding.etUsuario.setEnabled(false);
            binding.etContrasena.setEnabled(false);
        }
    }
}