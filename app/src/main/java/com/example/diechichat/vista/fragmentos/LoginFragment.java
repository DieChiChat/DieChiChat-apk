package com.example.diechichat.vista.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentLoginBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.example.diechichat.vistamodelo.NutriViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private NutriViewModel nutriVM;
    private ClienteViewModel cliVM;

    private LoginFragInterface mListener;

    private Cliente c;
    private Nutricionista n;

    private List<Nutricionista> tNutricionistas;
    private List<Cliente> tClientes;

    public interface LoginFragInterface {
        void onEntrarLoginFrag(Object obj);
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragInterface) {
            mListener = (LoginFragInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginFragInterface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ;
        }
        nutriVM = new ViewModelProvider(requireActivity()).get(NutriViewModel.class);
        cliVM = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        tNutricionistas = new ArrayList<>();
        tClientes = new ArrayList<>();
        extraerNutricionistas();
        extraerClientes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Listeners
        binding.btEntrar.setOnClickListener(btEntrar_OnClickListener);

        final EditText etUsuario = view.findViewById(R.id.etUsuario);
        final EditText etContrasena = view.findViewById(R.id.etContrasena);
        final Button btEntrar = view.findViewById(R.id.btEntrar);
        final ProgressBar pbLoading = view.findViewById(R.id.loading);


        //PARA GUARDAR CREDENCIALES EN EL MÓVIL
//        etContrasena.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//
//                }
//                return false;
//            }
//        });
    }

    private final View.OnClickListener btEntrar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            binding.loading.setVisibility(View.VISIBLE);

            if (!binding.etUsuario.getText().toString().isEmpty() || !binding.etContrasena.getText().toString().isEmpty()) {
                if (esAdministrador()) {
                    if (mListener != null) {
                        mListener.onEntrarLoginFrag(n);
                    }
                } else if (esCliente()) {
                    if (mListener != null) {
                        mListener.onEntrarLoginFrag(c);
                    }
                } else {
                    Snackbar.make(binding.getRoot(), "Introduce usuario y contraseña correctos", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                if (mListener != null) {
                    mListener.onEntrarLoginFrag(null);
                }
            }
        }
    };

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
        }
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

    /*************/


    public boolean esAdministrador() {
        for (Nutricionista nu : tNutricionistas) {
            if(nu.getUsuario().equals(binding.etUsuario.getText().toString()) && nu.getContrasena().equals(binding.etContrasena.getText().toString())) {
                n = nu;
                return true;
            }
        }
        return false;
    }

    public boolean esCliente() {
        for (Cliente cli : tClientes) {
            if(cli.getUsuario().equals(binding.etUsuario.getText().toString()) && cli.getContrasena().equals(binding.etContrasena.getText().toString())) {
                c = cli;
                return true;
            }
        }
        return false;
    }

    public void extraerNutricionistas() {
        nutriVM.getNutricionistaME().observe(this, new Observer<List<Nutricionista>>() {
            @Override
            public void onChanged(List<Nutricionista> nutris) {
                tNutricionistas.addAll(nutris);
            }
        });
    }

    public void extraerClientes() {
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clis) {
                tClientes.addAll(clis);
            }
        });
    }

}
