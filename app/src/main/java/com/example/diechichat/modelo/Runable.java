package com.example.diechichat.modelo;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class Runable implements java.lang.Runnable {
    private final Context mContext;
    private final String mquery;

    public Runable(Context mContext, String mquery) {
        this.mContext = mContext;
        this.mquery = mquery;
    }

    //devolver un intent con la información de la respuesta del json
    //lo que se pone dentro es lo del doInBackground
    @Override
    public void run() {
        String respuestaJson = NetworkUtils.getBookInfo(mquery);
        ArrayList<Alimento> tAlimentos = NetworkUtils.interpretarJson(respuestaJson);
        DatosAlimentos.getInstance().getAlimentos().addAll(tAlimentos);
        //No es necesario pasar el intent, ya lo coge con el singleton
        //Lo paso para comprobar si está lleno o no
        Intent i = new Intent();
        i.setAction("RESPUESTA_JSON");
        i.putParcelableArrayListExtra("json", tAlimentos);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
    }
}
