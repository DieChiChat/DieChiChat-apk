package com.example.diechichat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.diechichat.databinding.ActivityMainBinding;
import com.example.diechichat.databinding.AppBarMainBinding;
import com.example.diechichat.vista.dialogos.DlgConfirmacion;
import com.example.diechichat.vista.fragmentos.MiPerfilFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        DlgConfirmacion.DlgConfirmacionListener {


    private ActivityMainBinding bindingMain;
    private AppBarMainBinding bindingAppBar;

    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        bindingMain =ActivityMainBinding.inflate(getLayoutInflater());
        bindingAppBar=bindingMain.appBarMain;
        setContentView(bindingMain.getRoot());
        setSupportActionBar(bindingAppBar.mainToolbar);

        mNavC = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragCV)).getNavController();
        mAppBarConfiguration= new AppBarConfiguration.Builder(mNavC.getGraph()).
                setOpenableLayout(bindingMain.drawerLayout).
                build();
        NavigationUI.setupActionBarWithNavController(this,mNavC,mAppBarConfiguration);
        bindingMain.navView.setNavigationItemSelectedListener(navView_OnNavigationItemSelected);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavC, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void mostrarDlgSalir(){
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
        if(bindingMain.drawerLayout.isDrawerOpen(GravityCompat.START)){
            bindingMain.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
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
            if (item.getItemId()==R.id.menu_inicio){

            }else if (item.getItemId()==R.id.menu_miperfil){
                Intent i= new Intent(MainActivity.this,MiPerfilActivity.class);
                startActivity(i);

            }else if(item.getItemId()==R.id.menu_misclientes){
                Intent i= new Intent(MainActivity.this,MisClientesActivity.class);
                startActivity(i);
            }else if(item.getItemId()==R.id.menu_nuevocliente){
                Intent i= new Intent(MainActivity.this,NuevoClienteActivity.class);
                startActivity(i);
            }else{
                return false;
            }
            return false;
        }
    };
}