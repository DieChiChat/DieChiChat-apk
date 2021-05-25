package com.example.diechichat.modelo;

import java.util.ArrayList;
import java.util.List;

public class DatosAlimentos {
    private static DatosAlimentos mDatos;
    private List<Alimento> mAlimentos;

    private DatosAlimentos() {
        mAlimentos = new ArrayList<>();
    }

    public static DatosAlimentos getInstance() {
        if (mDatos == null)
            mDatos = new DatosAlimentos();
        return mDatos;
    }
    public List<Alimento> getAlimentos() {
        return mAlimentos;
    }
}
