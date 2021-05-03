package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.repositorio.NutriRepository;

import java.util.List;

public class NutriViewModel extends AndroidViewModel {

    /* ViewModel Dptos ****************************************************************************/

    private final NutriRepository mNutriRep;
    private LiveData<List<Nutricionista>> mNutricionista;

    private Nutricionista mLogin;
    private Nutricionista mNutriAEliminar;

    public NutriViewModel(@NonNull Application application) {
        super(application);
        mNutriRep = new NutriRepository(application);
        mNutricionista = null;
        mLogin = null;
        mNutriAEliminar = null;
    }

    /* Métodos Mantenimiento Departamentos ********************************************************/

    public LiveData<List<Nutricionista>> getNutricionistaME() {      // Multiple Events
        mNutricionista = mNutriRep.recuperarNutricionistasME();
        return mNutricionista;
    }

    public LiveData<List<Nutricionista>> getNutricionistaSE() {      // Single Event
        mNutricionista = mNutriRep.recuperarNutricionistasSE();
        return mNutricionista;
    }

    public LiveData<Boolean> altaNutricionista(Nutricionista nutri) {
        return mNutriRep.altaNutricionista(nutri);
    }

    public LiveData<Boolean> editarNutricionista(Nutricionista nutri) {
        return mNutriRep.editarNutricionista(nutri);
    }

    public LiveData<Boolean> bajaNutricionista(Nutricionista nutri) {
        return mNutriRep.bajaNutricionista(nutri);
    }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Nutricionista getLogin() {
        return mLogin;
    }

    public void setLogin(Nutricionista login) {
        mLogin = login;
    }

    public Nutricionista getNutriAEliminar() {
        return mNutriAEliminar;
    }

    public void setNutriAEliminar(Nutricionista nutriAEliminar) {
        mNutriAEliminar = nutriAEliminar;
    }

}
