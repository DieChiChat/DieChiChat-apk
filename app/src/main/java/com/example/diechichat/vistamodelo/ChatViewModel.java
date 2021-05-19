package com.example.diechichat.vistamodelo;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Chat;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.repositorio.ChatRepository;
import com.example.diechichat.repositorio.ClientesRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    /* ViewModel Chat ****************************************************************************/

    private final ChatRepository mChatRep;
    private LiveData<List<Chat>> mChat;

    private MutableLiveData<String> mFechaDlg;
    private final MutableLiveData<Bitmap> foto;

    private Cliente mLogin;
    private Chat mChatAEliminar;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.mChatRep = new ChatRepository(application);
        this.mChat = null;
        this.mLogin = null;
        this.mChatAEliminar = null;
        this.mFechaDlg= new MutableLiveData<>();
        this.foto = new MutableLiveData<>();
    }

    /* Métodos Chat ********************************************************/

    public LiveData<List<Chat>> getChatME() {      // Multiple Events
        mChat = mChatRep.recuperarChatSE();
        return mChat;
    }

    public LiveData<List<Chat>> getChatSE() {      // Single Event
        mChat = mChatRep.recuperarChatSE();
        return mChat;
    }

    public LiveData<Boolean> altaChat(Chat chat) {
        return mChatRep.altaChat(chat);
    }
    public LiveData<Boolean> bajaChat(Chat chat) { return mChatRep.borrarChat(chat); }

    /* Getters & Setters Objetos Persistentes *****************************************************/

    public Cliente getLogin() {
        return mLogin;
    }

    public void setLogin(Cliente login) {
        mLogin = login;
    }

    public Chat getChatAEliminar() {
        return mChatAEliminar;
    }
    public void setChatAEliminar(Chat chatAEliminar) {
        mChatAEliminar = chatAEliminar;
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
