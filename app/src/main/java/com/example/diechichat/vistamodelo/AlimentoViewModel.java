package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.Cliente;
import java.util.List;

public class AlimentoViewModel extends AndroidViewModel {

    /* ViewModel Alimentos ****************************************************************************/

    private final MutableLiveData<String> mFechaDlg;
    private final MutableLiveData<List<Alimento>> listadoAlimentos;

    private Cliente cliente;

    public AlimentoViewModel(@NonNull Application application) {
        super(application);
        this.mFechaDlg= new MutableLiveData<>();
        this.listadoAlimentos = new MutableLiveData<>();
    }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public void setFechaDlg(String fechaDlg){
        this.mFechaDlg.setValue(fechaDlg);
    }
    public LiveData<String> getFechaDlg() {
        return this.mFechaDlg;
    }

    public void setListadoAlimentos(List<Alimento> listadoAlimentos) { this.listadoAlimentos.setValue(listadoAlimentos); }
    public LiveData<List<Alimento>> getListado() { return this.listadoAlimentos; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Cliente getCliente() { return this.cliente;}
}
