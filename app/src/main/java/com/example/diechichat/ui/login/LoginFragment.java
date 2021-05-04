package com.example.diechichat.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentLoginBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vistamodelo.ClienteViewModel;
import com.example.diechichat.vistamodelo.NutriViewModel;

import java.util.List;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    private LoginFragInterface mListener;

    private Cliente c;
    private Nutricionista n;

    public interface LoginFragInterface {
        void onEntrarLoginFrag(Object obj);
//        void onEntrarLoginFrag(String usuario, String contrasena);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inits
        //binding.spLoginDptos.setAdapter(mAdaptadorDtpos);

        // Listeners
        binding.btEntrar.setOnClickListener(btEntrar_OnClickListener);

        final EditText etUsuario = view.findViewById(R.id.etUsuario);
        final EditText etContrasena = view.findViewById(R.id.etContrasena);
        final Button btEntrar = view.findViewById(R.id.btEntrar);
        final ProgressBar pbLoading = view.findViewById(R.id.loading);

        btEntrar.setEnabled(true);
        btEntrar.setBackgroundColor(R.string.colorFondo);
        btEntrar.setTextColor(R.string.colorLetras);

//        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                btEntrar.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    etUsuario.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    etContrasena.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });
////
//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                pbLoading.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
//                    updateUiWithUser(loginResult.getSuccess());
//                }
//            }
//        });
//
//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // ignore
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // ignore
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(etUsuario.getText().toString(), etContrasena.getText().toString());
//            }
//        };
//        etUsuario.addTextChangedListener(afterTextChangedListener);
//        etContrasena.addTextChangedListener(afterTextChangedListener);

        //PARA GUARDAR CREDENCIALES EN EL MÓVIL
        etContrasena.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(etUsuario.getText().toString(), etContrasena.getText().toString());
                }
                return false;
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);
//                loginViewModel.login(etUsuario.getText().toString(), etContrasena.getText().toString());
            }
        });


    }

    private final View.OnClickListener btEntrar_OnClickListener = new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            binding.btEntrar.setBackgroundTintList(ColorStateList.valueOf(R.color.gold));

            binding.loading.setVisibility(View.VISIBLE);
//            loginViewModel.login(binding.etUsuario.getText().toString(), binding.etContrasena.getText().toString());

            if (!binding.etUsuario.getText().toString().isEmpty() || !binding.etContrasena.getText().toString().isEmpty()) {
                if (esAdministrador()) {
                    if (mListener != null) {
                        mListener.onEntrarLoginFrag(n);
                    }
                } else if(esCliente()) {
                    if (mListener != null) {
                        mListener.onEntrarLoginFrag(c);
                    }
                }
            } else {
                if (mListener != null) {
                    mListener.onEntrarLoginFrag(null);
                }
            }
        }
    };

//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        if (getContext() != null && getContext().getApplicationContext() != null) {
//            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//        }
//    }

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
        final boolean[] esNutri = {false};
        NutriViewModel nutriVM = null;
        nutriVM.getNutricionistaME().observe(this, new Observer<List<Nutricionista>>() {
            @Override
            public void onChanged(List<Nutricionista> nutris) {
                for (int i = 0; i < nutris.size(); i++) {
                    if(nutris.get(i).getUsuario().equals(binding.etUsuario.getText().toString()) && nutris.get(i).getContrasena().equals(binding.etContrasena.getText().toString())) {
                        esNutri[0] = true;
                        n = nutris.get(i);
                        break;
                    }
                }
            }
        });
        return esNutri[0];
    }

    public boolean esCliente() {
        final boolean[] esCliente = {false};
        ClienteViewModel cliVM = null;
        cliVM.getClientesME().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                for (int i = 0; i < clientes.size(); i++) {
                    if(clientes.get(i).getUsuario().equals(binding.etUsuario.getText().toString()) && clientes.get(i).getContrasena().equals(binding.etContrasena.getText().toString())) {
                        esCliente[0] = true;
                        c = clientes.get(i);
                        break;
                    }
                }
            }
        });

        return esCliente[0];
    }


}