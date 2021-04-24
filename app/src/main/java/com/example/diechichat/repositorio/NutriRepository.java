package com.example.diechichat.repositorio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.diechichat.R;

import com.example.diechichat.modelo.AppDatabase;
import com.example.diechichat.modelo.Nutricionista;
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

public class NutriRepository {

    /* Repository Nutricionistas ***************************************************************************/

    public final AppDatabase mAppDB;

    public NutriRepository(Application application) {
        mAppDB = AppDatabase.getAppDatabase(application);
    }

    /* Clases Lógica Nutricionistas ************************************************************************/

    private class FirebaseLiveDataSE extends LiveData<List<Nutricionista>> {
        @Override
        protected void onActive() {
            super.onActive();
            mAppDB.getRefFS().collection("administrador").orderBy("id").get().addOnCompleteListener(nutrisSE_OnCompleteListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
        }

        private final OnCompleteListener<QuerySnapshot> nutrisSE_OnCompleteListener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Nutricionista> nutris = new ArrayList<>();
                    for (QueryDocumentSnapshot qds : task.getResult()) {
                        nutris.add(qds.toObject(Nutricionista.class));
                    }
                    setValue(nutris);
                }
            }
        };
    }

    private class FirebaseLiveDataME extends LiveData<List<Nutricionista>> {
        ListenerRegistration reg;

        @Override
        protected void onActive() {
            super.onActive();
            reg = mAppDB.getRefFS().collection("administrador").orderBy("id").addSnapshotListener(nutrisME_EventListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            reg.remove();
        }

        private final EventListener<QuerySnapshot> nutrisME_EventListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) return;
                List<Nutricionista> nutris = new ArrayList<>();
                for (QueryDocumentSnapshot qds : value) {
                    nutris.add(qds.toObject(Nutricionista.class));
                }
                setValue(nutris);
            }
        };
    }

    /* Métodos Lógica Nutricionistas ***********************************************************************/

    public LiveData<List<Nutricionista>> recuperarNutricionistasSE() {
        return new FirebaseLiveDataSE();
    }

    public LiveData<List<Nutricionista>> recuperarNutricionistasME() {
        return new FirebaseLiveDataME();
    }

    public LiveData<Boolean> altaNutricionista(@NonNull Nutricionista nutri) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("administrador").document(String.valueOf(nutri.getId())).set(nutri)
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

    public LiveData<Boolean> editarNutricionista(@NonNull Nutricionista nutri) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("administrador").document(String.valueOf(nutri.getId())).set(nutri, SetOptions.merge())
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

    public LiveData<Boolean> bajaNutricionista(@NonNull Nutricionista nutri) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        mAppDB.getRefFS().collection("administrador").document(String.valueOf(nutri.getId())).delete()
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
