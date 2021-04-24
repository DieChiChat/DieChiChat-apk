package com.example.diechichat.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.diechichat.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppDatabase {

    private static volatile AppDatabase db = null;  // Singleton

    public static DocumentReference refFS = null;

    public static final ExecutorService dbWriteExecutor = Executors.newSingleThreadExecutor();

    private AppDatabase() {
        // Patrón Singleton
    }

    public DocumentReference getRefFS() {
        return refFS;
    }

    public static AppDatabase getAppDatabase(Context context) {
        if (db == null) {
            synchronized (AppDatabase.class) {
                if (db == null) {
                    db = new AppDatabase();

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    String nombreBD = pref.getString(context.getResources().getString(R.string.Firebase_name_key), "");
                    if (!nombreBD.equals("")) {
                        // Creamos Administrador 0 admin
                        Nutricionista nutri = new Nutricionista();
                        nutri.setId(0);
                        nutri.setNombre("admin");
                        nutri.setContrasena("admin");
                        nutri.setApellidos("apellidos admin");
                        nutri.setUsuario("admin");
                        // Ini FirebaseFireStore
                        FirebaseFirestore dbFS = FirebaseFirestore.getInstance();
                        refFS = dbFS.document("proyectos/" + nombreBD);
                        // Guardamos Administrador 0 admin (si no existe ya)
                        refFS.collection("administrador").document(String.valueOf(nutri.getId())).set(nutri);
                    }

                }
            }
        }
        return db;
    }

    public static boolean cerrarAppDatabase() {
        if (db != null) {
            refFS = null;
            db = null;
            return true;
        }
        return false;
    }

}
