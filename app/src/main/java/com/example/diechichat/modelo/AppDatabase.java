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

//                    En caso de querer crear usuario nutricionista siempre que se accede
//                    Nutricionista n = new Nutricionista();
//                    n.setNombre("Agustín");
//                    n.setApellidos("Alarcón Nicolás");
//                    n.setId(0);
//                    n.setUsuario("admin");
//                    n.setContrasena("admin");

                    FirebaseFirestore dbFS = FirebaseFirestore.getInstance();
                    refFS = dbFS.document("proyectos/" + nombreBD);
//                    refFS.collection("administrador").document(String.valueOf(n.getId())).set(n);

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
