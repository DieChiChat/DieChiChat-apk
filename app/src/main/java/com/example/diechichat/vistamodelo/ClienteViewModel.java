package com.example.diechichat.vistamodelo;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.repositorio.ClientesRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClienteViewModel extends AndroidViewModel {

    /* ViewModel Clientes ****************************************************************************/

    private final ClientesRepository mCliRep;
    private LiveData<List<Cliente>> mCliente;

    private MutableLiveData<String> mFechaDlg;
    private final MutableLiveData<Bitmap> foto;

    private Cliente mLogin;
    private Cliente mClienteAEliminar;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.mCliRep = new ClientesRepository(application);
        this.mCliente = null;
        this.mLogin = null;
        this.mClienteAEliminar = null;
        this.mFechaDlg= new MutableLiveData<>();
        this.foto = new MutableLiveData<>();
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

    public void setmFechaDlg(String fechaDlg){
        mFechaDlg.setValue(fechaDlg);
    }
    public LiveData<String> getmFechaDlg() {
        return mFechaDlg;
    }

    public void setFoto(Bitmap foto) { this.foto.setValue(foto); }
    public LiveData<Bitmap> getFoto() { return foto; }


}
