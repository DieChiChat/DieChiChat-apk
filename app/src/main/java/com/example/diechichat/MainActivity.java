package com.example.diechichat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.diechichat.databinding.ActivityMainBinding;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.ui.login.LoginFragment;
import com.example.diechichat.ui.login.LoginViewModel;
import com.example.diechichat.vistamodelo.MainViewModel;
import com.example.diechichat.vistamodelo.NutriViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragInterface {

    private MainViewModel mainVM;
    private LoginViewModel loginVM;
    private NutriViewModel nutriVM;

    private NavController mNavC;

    private ActivityMainBinding bindingMain;
//    private AppBarMainBinding bindingAppBar;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        Toolbar toolbar = findViewById(R.id.toolbar);

        bindingMain = ActivityMainBinding.inflate(getLayoutInflater());
        //bindingAppBar = bindingMain.appBarMain();
//        setSupportActionBar(bindingAppBar.mainToolbar);

//        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragLogin)).getNavController();

//        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavC.getGraph()).setOpenableLayout(bindingMain.drawerLayout).build();
//        NavigationUI.setupActionBarWithNavController(this, mNavC, mAppBarConfiguration);
//        bindingMain.navView.setNavigationItemSelectedListener(navView_OnNavigationItemSelected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

//    @Override
//    public void onEntrarLoginFrag(String usuario, String contrasena) {
//        loginVM.login(usuario, contrasena);
//    }

    @Override
    public void onEntrarLoginFrag(Object obj) {
        mainVM.setLogin(obj);
        if (obj instanceof Nutricionista) {
            //loginVM.login(((Nutricionista) obj).getUsuario(), ((Nutricionista) obj).getContrasena());
            Snackbar.make(bindingMain.getRoot(), "ha entrado en login nutricionista", Snackbar.LENGTH_SHORT).show();
        } else if (obj instanceof Cliente) {
            //loginVM.login(((Nutricionista) obj).getUsuario(), ((Nutricionista) obj).getContrasena());
            Snackbar.make(bindingMain.getRoot(), "ha entrado en login cliente", Snackbar.LENGTH_SHORT).show();
        }
        finish();
        mNavC.navigateUp();
    }
}