package com.example.diechichat.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.diechichat.R;
import com.example.diechichat.databinding.ActivityMainBinding;
import com.example.diechichat.databinding.AppBarMainBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.vista.dialogos.DlgConfirmacion;
import com.example.diechichat.vista.fragmentos.LoginFragment;
import com.example.diechichat.vista.fragmentos.NuevoCienteFragment;
import com.example.diechichat.vistamodelo.ChatViewModel;
import com.example.diechichat.vistamodelo.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginFragInterface,
        DlgConfirmacion.DlgConfirmacionListener {

    private MainViewModel mainVM;
    private ChatViewModel chatVM;

    private ActivityMainBinding bindingMain;
    private AppBarMainBinding bindingAppBar;

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingMain = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bindingMain.getRoot());

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragCV)).getNavController();
        chatVM = new ViewModelProvider(this).get(ChatViewModel.class);

        bindingMain.navView.setNavigationItemSelectedListener(navView_OnNavigationItemSelected);

        //BLOQUEAR NAVIGATION DRAWER PARA QUE NO SE MUESTRE ANTES DE INICIAR SESIÓN
        bindingMain.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mainVM = new ViewModelProvider(this).get(MainViewModel.class);
        if(mainVM.getLogin() == null && savedInstanceState == null) {
            mNavC.navigate(R.id.action_nav_inicio_to_loginFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavC, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void mostrarDlgSalir() {
        //Lanzamos DlgConfirmacion
        Bundle bundle = new Bundle();
        bundle.putInt("titulo", R.string.app_name);
        bundle.putInt("mensaje", R.string.msg_DlgConfirmacion_Salir);
        bundle.putString("tag", "tagConfirmacion_Salir");
        mNavC.navigate(R.id.action_global_dlgConfirmacionMain, bundle);
    }

    @Override
    public void onDlgConfirmacionPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onDlgConfirmacionNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onBackPressed() {
        if (bindingMain.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            bindingMain.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mostrarDlgSalir();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private final NavigationView.OnNavigationItemSelectedListener navView_OnNavigationItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menu_inicio) {
                Intent i = null;
//                if(mainVM.getLogin() instanceof Nutricionista) {
//                    bundle = new Bundle();
//                    bundle.putParcelable("usuario",(Nutricionista) mainVM.getLogin());
//                } else
//                 if(mainVM.getLogin() instanceof Cliente) {
//                    i = new Intent();
//                    i.putExtra("usuario",(Cliente) mainVM.getLogin());
//                }
//                if(i != null) {
//                    startActivity(i);
//                }
            } else if (item.getItemId() == R.id.menu_miperfil) {
                if(mainVM.getLogin() != null) {
                    Intent i = null;
                    if(mainVM.getLogin() instanceof Nutricionista) {
                        i = new Intent(MainActivity.this, MiPerfilActivity.class);
                        i.putExtra("login",(Nutricionista) mainVM.getLogin());
                        i.putExtra("op",NuevoCienteFragment.OP_EDITAR);
                    } else if(mainVM.getLogin() instanceof Cliente) {
                        i = new Intent(MainActivity.this, NuevoClienteActivity.class);
                        i.putExtra("login",(Cliente) mainVM.getLogin());
                        i.putExtra("op",NuevoCienteFragment.OP_EDITAR);
                    }
                    if(i != null) {
                        startActivity(i);
                    }
                }
            } else if (item.getItemId() == R.id.menu_misclientes) {
                Intent i = new Intent(MainActivity.this, MisClientesActivity.class);
                startActivity(i);
            } else if (item.getItemId() == R.id.menu_nuevocliente) {
                Intent i = new Intent(MainActivity.this, NuevoClienteActivity.class);
                i.putExtra("login", ((Nutricionista) mainVM.getLogin()).getId());
                startActivity(i);
            } else if (item.getItemId() == R.id.menu_chat) {
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                if(mainVM.getLogin() instanceof Nutricionista) {
                    i.putExtra("login", (Nutricionista)mainVM.getLogin());
                    chatVM.setLoginNutricionista((Nutricionista) mainVM.getLogin());
                } else if(mainVM.getLogin() instanceof Cliente) {
                    i.putExtra("login", (Cliente)mainVM.getLogin());
                    chatVM.setLoginCliente((Cliente) mainVM.getLogin());
                }
                startActivity(i);
//                Snackbar.make(bindingMain.getRoot(), R.string.msg_mantenimiento, Snackbar.LENGTH_SHORT).show();
            } else {
                return false;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.salir) {
            mostrarDlgSalir();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Métodos login

    @Override
    public void onEntrarLoginFrag(Object obj) {
        if (obj != null) {
            mainVM.setLogin(obj);
            bindingAppBar = bindingMain.appBarMain;
            setSupportActionBar(bindingAppBar.mainToolbar);
            mAppBarConfiguration = new AppBarConfiguration.Builder(mNavC.getGraph()).setOpenableLayout(bindingMain.drawerLayout).build();
            NavigationUI.setupActionBarWithNavController(this, mNavC, mAppBarConfiguration);

            //DESBLOQUEAR NAVIGATION DRAWER
            bindingMain.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

//            OCULTAR OPCIONES MENÚ EN CASO DE CLIENTE
            if(mainVM.getLogin() instanceof Cliente) {
                bindingMain.drawerLayout.findViewById(R.id.menu_nuevocliente).setVisibility(View.INVISIBLE);
                bindingMain.drawerLayout.findViewById(R.id.menu_misclientes).setVisibility(View.INVISIBLE);

                //TODO: cambiar posición de items del menú drawer
            }

            mNavC.navigateUp();
            if(mainVM.getLogin() instanceof Nutricionista) {
            }
        } else {
            Snackbar.make(bindingMain.getRoot(), "Introduce usuario y contraseña", Snackbar.LENGTH_SHORT).show();
        }
    }

}
