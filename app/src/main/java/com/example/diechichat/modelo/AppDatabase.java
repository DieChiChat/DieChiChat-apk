package com.example.diechichat.modelo;

import android.content.Context;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppDatabase {

    private static volatile AppDatabase db = null;  // Singleton

    public static DocumentReference refFS = null;

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

                    String nombreBD = "DieChiChat";
                    if (!nombreBD.equals("")) {
                        // Se crea Administrador 0 admin
                        Nutricionista nutri = new Nutricionista();
                        nutri.setId(0);
                        nutri.setNombre("admin");
                        nutri.setContrasena("admin");
                        nutri.setApellidos("apellidos admin");
                        nutri.setUsuario("admin");
                        // Ini FirebaseFireStore
//                        FirebaseApp.initializeApp(this);

                        FirebaseFirestore dbFS = FirebaseFirestore.getInstance();
                        refFS = dbFS.document("proyectos/" + nombreBD);
                        // Se guarda Administrador 0 admin (si no existe ya)
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
