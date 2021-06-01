package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diechichat.modelo.AppDatabase;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.repositorio.NutriRepository;

public class MainViewModel extends AndroidViewModel {

    private Object mLogin;
    private NutriRepository nutriRep;

    /* ViewModel Main *****************************************************************************/

    public MainViewModel(@NonNull Application application) {
        super(application);
        nutriRep = new NutriRepository(application);
        mLogin = null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Cerramos AppDatabase
        if (!AppDatabase.cerrarAppDatabase()) {
            ;
        }
    }

    public LiveData<Boolean> editarNutricionista(Nutricionista nutricionista) {
        return nutriRep.editarNutricionista(nutricionista);
    }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Object getLogin() {
        return mLogin;
    }
    public void setLogin(Object login) {
        mLogin = login;
    }

}
