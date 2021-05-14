package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.Nutricionista;
import com.example.diechichat.repositorio.ClientesRepository;
import com.example.diechichat.repositorio.NutriRepository;

import java.util.List;

public class ClienteViewModel extends AndroidViewModel {

    /* ViewModel Dptos ****************************************************************************/

    private final ClientesRepository mCliRep;
    private LiveData<List<Cliente>> mCliente;

    private Cliente mLogin;
    private Cliente mClienteAEliminar;
    private final MutableLiveData<String> fechaDialogo;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        mCliRep = new ClientesRepository(application);
        mCliente = null;
        mLogin = null;
        mClienteAEliminar = null;
        fechaDialogo= new MutableLiveData<>();
    }

    /* Métodos Mantenimiento Departamentos ********************************************************/

    public LiveData<List<Cliente>> getClientesME() {      // Multiple Events
        mCliente = mCliRep.recuperarClienteSE();
        return mCliente;
    }

    public LiveData<List<Cliente>> getClientesSE() {      // Single Event
        mCliente = mCliRep.recuperarClienteSE();
        return mCliente;
    }

    public LiveData<Boolean> altaNutricionista(Cliente cli) {
        return mCliRep.altaCliente(cli);
    }

    public LiveData<Boolean> editarCliente(Cliente cli) {
        return mCliRep.editarCliente(cli);
    }

    public LiveData<Boolean> bajaCliente(Cliente cli) {
        return mCliRep.bajaCliente(cli);
    }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Cliente getLogin() {
        return mLogin;
    }

    public void setLogin(Cliente login) {
        mLogin = login;
    }

    public Cliente getClienteAEliminar() {
        return mClienteAEliminar;
    }

    public void setCliAEliminar(Cliente cliAEliminar) {
        mClienteAEliminar = cliAEliminar;
    }

    public void setFechaDialogo(String fechaDialogo) { this.fechaDialogo.setValue(fechaDialogo); }
    public LiveData<String> getFechaDialogo() { return fechaDialogo; }

}
