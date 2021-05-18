package com.example.diechichat.vistamodelo;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.repositorio.AlimentosRepository;
import com.example.diechichat.repositorio.ClientesRepository;

import java.util.List;

public class AlimentoViewModel extends AndroidViewModel {

    /* ViewModel Alimentos ****************************************************************************/

    private final AlimentosRepository mAliRep;
    private LiveData<List<Alimento>> mAlimento;

    private MutableLiveData<String> mFechaDlg;
    private final MutableLiveData<Bitmap> foto;

    private Cliente mLogin;
    private Alimento mAlimentoAEliminar;

    public AlimentoViewModel(@NonNull Application application) {
        super(application);
        this.mAliRep = new AlimentosRepository(application);
        this.mAlimento = null;
        this.mLogin = null;
        this.mAlimentoAEliminar = null;
        this.mFechaDlg= new MutableLiveData<>();
        this.foto = new MutableLiveData<>();
    }

    /* Métodos Alimentos ********************************************************/

    public LiveData<List<Alimento>> getAlimentosME() {      // Multiple Events
        mAlimento = mAliRep.recuperarAlimentoME();
        return mAlimento;
    }

    public LiveData<List<Alimento>> getAlimentosSE() {      // Single Event
        mAlimento = mAliRep.recuperarAlimentoSE();
        return mAlimento;
    }

    public LiveData<Boolean> altaAlimento(Alimento ali) {
        return mAliRep.altaAlimento(ali);
    }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Cliente getLogin() {
        return mLogin;
    }
    public void setLogin(Cliente login) {
        mLogin = login;
    }

    public Alimento getAlimentoAEliminar() {
        return mAlimentoAEliminar;
    }
    public void setAliAEliminar(Alimento aliAEliminar) {
        mAlimentoAEliminar = aliAEliminar;
    }

    public void setmFechaDlg(String fechaDlg){
        mFechaDlg.setValue(fechaDlg);
    }
    public LiveData<String> getmFechaDlg() {
        return mFechaDlg;
    }


}
