package com.example.diechichat.repositorio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.AppDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AlimentosRepository {

    /* Repository Alimentos ***************************************************************************/

    public final AppDatabase mAppDB;

    public AlimentosRepository(Application application) {
        mAppDB = AppDatabase.getAppDatabase(application);
    }

    /* Clases Lógica Alimentos ************************************************************************/

    private class FirebaseLiveDataSE extends LiveData<List<Alimento>> {
        @Override
        protected void onActive() {
            super.onActive();
            mAppDB.getRefFS().collection("alimentos").orderBy("id").get().addOnCompleteListener(alisSE_OnCompleteListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }

        private final OnCompleteListener<QuerySnapshot> alisSE_OnCompleteListener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Alimento> alis = new ArrayList<>();
                    for (QueryDocumentSnapshot qds : task.getResult()) {
                        alis.add(qds.toObject(Alimento.class));
                    }
                    setValue(alis);
                }
            }
        };
    }

    private class FirebaseLiveDataME extends LiveData<List<Alimento>> {
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

        private final EventListener<QuerySnapshot> alisME_EventListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) return;
                List<Alimento> alis = new ArrayList<>();
                for (QueryDocumentSnapshot qds : value) {
                    alis.add(qds.toObject(Alimento.class));
                }
                setValue(alis);
            }
        };
    }

    /* Métodos Lógica Alimentos ***********************************************************************/

    public LiveData<List<Alimento>> recuperarAlimentoSE() {
        return new AlimentosRepository.FirebaseLiveDataSE();
    }

    public LiveData<List<Alimento>> recuperarAlimentoME() {
        return new AlimentosRepository.FirebaseLiveDataME();
    }

    public LiveData<Boolean> altaAlimento(@NonNull Alimento ali) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("alimentos").document(String.valueOf(ali.getId())).set(ali)
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
