package com.example.diechichat.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.diechichat.R;
import com.example.diechichat.databinding.FragmentLoginBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    private LoginFragInterface mListener;

    public interface LoginFragInterface {
//        void onEntrarLoginFrag(Object obj);
        void onEntrarLoginFrag(String usuario, String contrasena);
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


        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        final EditText etUsuario = view.findViewById(R.id.etUsuario);
        final EditText etContrasena = view.findViewById(R.id.etContrasena);
        final Button btEntrar = view.findViewById(R.id.btEntrar);
        final ProgressBar pbLoading = view.findViewById(R.id.loading);

        btEntrar.setEnabled(true);

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
                    loginViewModel.login(etUsuario.getText().toString(), etContrasena.getText().toString());
                }
                return false;
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);
                loginViewModel.login(etUsuario.getText().toString(), etContrasena.getText().toString());
            }
        });


    }

    private final View.OnClickListener btEntrar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            binding.loading.setVisibility(View.VISIBLE);
            loginViewModel.login(binding.etUsuario.getText().toString(), binding.etContrasena.getText().toString());

            Cliente c;
            if (!binding.etUsuario.getText().toString().isEmpty() || !binding.etContrasena.getText().toString().isEmpty()) {
                if (mListener != null) {
                    mListener.onEntrarLoginFrag(binding.etUsuario.getText().toString(), binding.etContrasena.getText().toString());
                }
            } else {
                if (mListener != null) {
                    mListener.onEntrarLoginFrag("", "");
                }
            }
//            List<Nutricionista> nutris = ;
//            if (!binding.etUsuario.getText().toString().isEmpty()) {
//                Nutricionista nutri = mAdaptadorNutricionista.getItem(binding.etUsuario.getText().toString());
//                if (nutri.getContrasena().equals(binding.etContrasena.getText().toString())) {
//                    if (mListener != null)
//                        mListener.onEntrarLoginFrag(nutri);
//                } else {
//                    if (mListener != null)
//                        mListener.onEntrarLoginFrag(null);
//                }
//            }
        }
    };

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

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
}