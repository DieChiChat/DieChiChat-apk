package com.example.diechichat.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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
import com.example.diechichat.vistamodelo.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginFragInterface,
        DlgConfirmacion.DlgConfirmacionListener {

    private MainViewModel mainVM;

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

        bindingMain.navView.setNavigationItemSelectedListener(navView_OnNavigationItemSelected);

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
                Intent i = new Intent(MainActivity.this, DietaActivity.class);
                startActivity(i);
            } else if (item.getItemId() == R.id.menu_miperfil) {
                Intent i = null;
                if(mainVM.getLogin() instanceof Nutricionista) {
                    i = new Intent(MainActivity.this, MiPerfilActivity.class);
                    Bundle bundleNu = new Bundle();
                    bundleNu.putParcelable("nutricionista",(Nutricionista) mainVM.getLogin());
                    bundleNu.putInt("op", NuevoCienteFragment.OP_EDITAR);
                    i.putExtra("nutricionista", bundleNu);
                } else if(mainVM.getLogin() instanceof Cliente) {
                    i = new Intent(MainActivity.this, NuevoClienteActivity.class);
                    Bundle bundleCli= new Bundle();
                    bundleCli.putParcelable("clienteVer",(Cliente) mainVM.getLogin());
                    bundleCli.putInt("op", NuevoCienteFragment.OP_EDITAR);
                    i.putExtra("clienteVer", bundleCli);
                }
                if(i != null) {
                    startActivity(i);
                }
            } else if (item.getItemId() == R.id.menu_misclientes) {
                Intent i = new Intent(MainActivity.this, MisClientesActivity.class);
                startActivity(i);
            } else if (item.getItemId() == R.id.menu_nuevocliente) {
                Intent i = new Intent(MainActivity.this, NuevoClienteActivity.class);
                i.putExtra("login", ((Nutricionista) mainVM.getLogin()).getId());
                startActivity(i);
            } else if (item.getItemId() == R.id.menu_chat) {
//                Intent i = new Intent(MainActivity.this, ChatActivity.class);
//                startActivity(i);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Métodos login

    @Override
    public void onEntrarLoginFrag(Object obj) {
        if (obj != null) {
            mainVM.setLogin(obj);
            if (obj instanceof Nutricionista) {
                Snackbar.make(bindingMain.getRoot(), (R.string.msg_bienvenida + " " + ((Nutricionista) obj).getNombreCompleto()), Snackbar.LENGTH_SHORT).show();

            } else if (obj instanceof Cliente) {
                Snackbar.make(bindingMain.getRoot(), (R.string.msg_bienvenida + " " + ((Cliente) obj).getNombreCompleto()), Snackbar.LENGTH_SHORT).show();
            }

            bindingAppBar = bindingMain.appBarMain;
            setSupportActionBar(bindingAppBar.mainToolbar);
            mAppBarConfiguration = new AppBarConfiguration.Builder(mNavC.getGraph()).setOpenableLayout(bindingMain.drawerLayout).build();
            NavigationUI.setupActionBarWithNavController(this, mNavC, mAppBarConfiguration);

            mNavC.navigateUp();
        } else {
            Snackbar.make(bindingMain.getRoot(), "Introduce usuario y contraseña", Snackbar.LENGTH_SHORT).show();
        }
    }

}
