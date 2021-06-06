package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Chat implements Parcelable {

    /* Atributos **********************************************************************************/

    private String id;
    private String tMensajes;
    private String horaMensaje;
    private int pos;

    /* Constructor ********************************************************************************/

    public Chat() {
        asignarHora();
    }

    protected Chat(Parcel in) {
        id = in.readString();
        tMensajes = in.readString();
        horaMensaje = in.readString();
        pos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tMensajes);
        dest.writeString(horaMensaje);
        dest.writeInt(pos);
    }

    /* Métodos Parcelable *************************************************************************/

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    /* Métodos Getters&Setters ********************************************************************/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return tMensajes;
    }

    public void setMensaje(String mensajeTexto) {
        this.tMensajes = mensajeTexto;
    }

    public String getHoraMensaje() {
        return horaMensaje;
    }

    public void setHoraMensaje(String horaMensaje) {
        this.horaMensaje = horaMensaje;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void asignarHora() {
        Calendar calendario = Calendar.getInstance();
        String hora = calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND);
        this.horaMensaje = hora;
    }

}
