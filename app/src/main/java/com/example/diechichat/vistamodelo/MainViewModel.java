package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.diechichat.modelo.AppDatabase;

public class MainViewModel extends AndroidViewModel {

    private Object mLogin;

    /* ViewModel Main *****************************************************************************/

    public MainViewModel(@NonNull Application application) {
        super(application);
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

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Object getLogin() {
        return mLogin;
    }

    public void setLogin(Object login) {
        mLogin = login;
    }

}
