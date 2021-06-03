package com.example.diechichat.repositorio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.diechichat.modelo.Alimento;
import com.example.diechichat.modelo.AppDatabase;
import com.example.diechichat.modelo.Cliente;
import com.example.diechichat.modelo.FiltroAlimentos;
import com.example.diechichat.vista.fragmentos.DietaFragment;
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
    private FiltroAlimentos filtroAlimentos;

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
        mAppDB.getRefFS().collection("clientes").document(cli.getId()).set(cli)
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
        mAppDB.getRefFS().collection("clientes").document(cli.getId()).set(cli, SetOptions.merge())
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
        mAppDB.getRefFS().collection("clientes").document(cli.getId()).delete()
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

    /**Recuperar Alimentos de Cliente **************************************/
    public LiveData<List<Alimento>> recuperarAlimentosSE(FiltroAlimentos filtroAlimentos) {
        this.filtroAlimentos = filtroAlimentos;
        return new ClientesRepository.FirebaseLiveDataAlimentoSE();
    }

    private class FirebaseLiveDataAlimentoSE extends LiveData<List<Alimento>> {
        ListenerRegistration reg;
        @Override
        protected void onActive() {
            super.onActive();

            reg = mAppDB.getRefFS().collection("clientes").whereEqualTo("id", filtroAlimentos.getCliente().getId()).addSnapshotListener(alisSE_OnCompleteListener);
//            mAppDB.getRefFS().collection("clientes").orderBy("id").get().addOnCompleteListener(alisSE_OnCompleteListener);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            reg.remove();
        }

        private final EventListener<QuerySnapshot> alisSE_OnCompleteListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) return;
                List<Alimento> alimentos = new ArrayList<>();
                for (QueryDocumentSnapshot qds : value) {
                    if(filtroAlimentos.getCliente() != null) {
                        if (qds.toObject(Cliente.class).getDesayuno().size() > 0) {
                            List<Alimento> tLista = qds.toObject(Cliente.class).getDesayuno();
                            for(Alimento a: tLista) {
                                alimentos.add(a);
                            }
                        } else if (filtroAlimentos.getTipo() == DietaFragment.OP_COMIDA) {
                            if (qds.toObject(Cliente.class).getComida().size() > 0) {
                                List<Alimento> tLista = qds.toObject(Cliente.class).getComida();
                                for (Alimento a : tLista) {
                                    alimentos.add(a);
                                }
                            }
                        } else if (filtroAlimentos.getTipo() == DietaFragment.OP_CENA) {
                            if (qds.toObject(Cliente.class).getCena().size() > 0) {
                                List<Alimento> tLista = qds.toObject(Cliente.class).getCena();
                                for (Alimento a : tLista) {
                                    alimentos.add(a);
                                }
                            }
                        } else if (filtroAlimentos.getTipo() == DietaFragment.OP_OTROS) {
                            if (qds.toObject(Cliente.class).getOtros().size() > 0) {
                                List<Alimento> tLista = qds.toObject(Cliente.class).getOtros();
                                for (Alimento a : tLista) {
                                    alimentos.add(a);
                                }
                            }
                        }
                    }
                }
                setValue(alimentos);
            }
        };

    }
}
