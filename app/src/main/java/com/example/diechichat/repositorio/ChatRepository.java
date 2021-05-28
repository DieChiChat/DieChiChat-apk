package com.example.diechichat.repositorio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.AppDatabase;
import com.example.diechichat.modelo.Chat;
import com.example.diechichat.modelo.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    /* Repository Chat ***************************************************************************/

    public final AppDatabase mAppDB;
    //private FiltroClis filtro;

    public ChatRepository(Application application) {
        mAppDB = AppDatabase.getAppDatabase(application);
    }

    /* Clases Lógica Chat ************************************************************************/

    private class FirebaseLiveDataSE extends LiveData<List<Chat>> {
        @Override
        protected void onActive() {
            super.onActive();
            mAppDB.getRefFS().collection("chat").orderBy("id").get().addOnCompleteListener(chatSE_OnCompleteListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }

        private final OnCompleteListener<QuerySnapshot> chatSE_OnCompleteListener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Chat> chat = new ArrayList<>();
                    for (QueryDocumentSnapshot qds : task.getResult()) {
                        chat.add(qds.toObject(Chat.class));
                    }
                    setValue(chat);
                }
            }
        };
    }

    private class FirebaseLiveDataME extends LiveData<List<Chat>> {
        ListenerRegistration reg;

        @Override
        protected void onActive() {
            super.onActive();
//            if(filtro.getEstado().equals("%")) {
//                reg = mAppDB.getRefFS().collection("clientes").whereEqualTo("idAdmin", filtro.getIdDpto()).whereGreaterThanOrEqualTo("fecha", filtro.getFecha()).addSnapshotListener(incsME_EventListener);
//            }else{
//                reg = mAppDB.getRefFS().collection("incs").whereEqualTo("idDpto", filtro.getIdDpto()).whereGreaterThanOrEqualTo("fecha", filtro.getFecha()).whereEqualTo("estado", filtro.getEstado().equals("1")).addSnapshotListener(incsME_EventListener);
//            }
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            reg.remove();
        }

        private final EventListener<QuerySnapshot> chatME_EventListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) return;
                List<Chat> chat = new ArrayList<>();
                for (QueryDocumentSnapshot qds : value) {
                    chat.add(qds.toObject(Chat.class));
                }
                setValue(chat);
            }
        };
    }

    /* Métodos Lógica Clientes ***********************************************************************/

    public LiveData<List<Chat>> recuperarChatSE() {
        return new ChatRepository.FirebaseLiveDataSE();
    }

//    public LiveData<List<Chat>> recuperarChatME(FiltroChat filtro) {
//        this.filtro = filtro;
//        return new ChatRepository.FirebaseLiveDataME();
//    }

    public LiveData<Boolean> altaChat(@NonNull Chat chat) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("chat").document(chat.getId()).set(chat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.postValue(false);
                    }
                });
        return result;
    }

    public LiveData<Boolean> borrarChat(@NonNull Chat chat) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("chat").document(chat.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.postValue(false);
                    }
                });
        return result;
    }

}
