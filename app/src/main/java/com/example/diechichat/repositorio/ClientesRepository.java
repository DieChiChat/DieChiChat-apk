package com.example.diechichat.repositorio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.AppDatabase;
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

public class ClientesRepository {

    /* Repository Clientes ***************************************************************************/

    public final AppDatabase mAppDB;
    //private FiltroClis filtro;

    public ClientesRepository(Application application) {
        mAppDB = AppDatabase.getAppDatabase(application);
    }

    /* Clases Lógica Clientes ************************************************************************/

    private class FirebaseLiveDataSE extends LiveData<List<Cliente>> {
        @Override
        protected void onActive() {
            super.onActive();
            mAppDB.getRefFS().collection("clientes").orderBy("id").get().addOnCompleteListener(clisSE_OnCompleteListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }

        private final OnCompleteListener<QuerySnapshot> clisSE_OnCompleteListener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Cliente> clis = new ArrayList<>();
                    for (QueryDocumentSnapshot qds : task.getResult()) {
                        clis.add(qds.toObject(Cliente.class));
                    }
                    setValue(clis);
                }
            }
        };
    }

    private class FirebaseLiveDataME extends LiveData<List<Cliente>> {
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

        private final EventListener<QuerySnapshot> clisME_EventListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) return;
                List<Cliente> clis = new ArrayList<>();
                for (QueryDocumentSnapshot qds : value) {
                    clis.add(qds.toObject(Cliente.class));
                }
                setValue(clis);
            }
        };
    }

    /* Métodos Lógica Clientes ***********************************************************************/

    public LiveData<List<Cliente>> recuperarClienteSE() {
        return new ClientesRepository.FirebaseLiveDataSE();
    }

//    public LiveData<List<Cliente>> recuperarClienteME(FiltroClis filtro) {
//        this.filtro = filtro;
//        return new ClientesRepository.FirebaseLiveDataME();
//    }

    public LiveData<Boolean> altaCliente(@NonNull Cliente cli) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("clientes").document(String.valueOf(cli.getIdAdmin()) + cli.getFechaNacimiento() + cli.getId()).set(cli)
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

    public LiveData<Boolean> editarCliente(@NonNull Cliente cli) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("clientes").document(String.valueOf(cli.getIdAdmin()) + cli.getFechaNacimiento() + cli.getUsuario()).set(cli, SetOptions.merge())
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

    public LiveData<Boolean> bajaCliente(@NonNull Cliente cli) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("clientes").document(String.valueOf(cli.getIdAdmin()) + cli.getFechaNacimiento() + cli.getUsuario()).delete()
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
