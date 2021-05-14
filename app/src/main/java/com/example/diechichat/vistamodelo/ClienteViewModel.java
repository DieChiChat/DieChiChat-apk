package com.example.diechichat.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.repositorio.ClientesRepository;

import java.util.List;

public class ClienteViewModel extends AndroidViewModel {

    /* ViewModel Clientes ****************************************************************************/

    private final ClientesRepository mCliRep;
    private LiveData<List<Cliente>> mCliente;

    private Cliente mLogin;
    private Cliente mClienteAEliminar;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        mCliRep = new ClientesRepository(application);
        mCliente = null;
        mLogin = null;
        mClienteAEliminar = null;
    }

    /* Métodos Clientes ********************************************************/

    public LiveData<List<Cliente>> getClientesME() {      // Multiple Events
        mCliente = mCliRep.recuperarClienteSE();
        return mCliente;
    }

    public LiveData<List<Cliente>> getClientesSE() {      // Single Event
        mCliente = mCliRep.recuperarClienteSE();
        return mCliente;
    }

    public LiveData<Boolean> altaCliente(Cliente cli) {
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

}
