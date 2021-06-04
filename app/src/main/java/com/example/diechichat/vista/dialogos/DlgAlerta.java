package com.example.diechichat.vista.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.diechichat.R;

public class DlgAlerta extends DialogFragment {

    private int mTitulo;
    private int mMensaje;

    public void setTitulo(int titulo) {
        mTitulo = titulo;
    }

    public void setMensaje(int mensaje) {
        mMensaje = mensaje;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(mTitulo);
            builder.setMessage(mMensaje);
            builder.setPositiveButton(R.string.btOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ;
                }
            });
            return builder.create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }

}
