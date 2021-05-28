package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Chat implements Parcelable {

    /* Atributos **********************************************************************************/

    private String id;
    private String tMensajes;
    private long horaMensaje;
    private int pos;

    /* Constructor ********************************************************************************/

    public Chat() {
        this.horaMensaje = new Date().getTime();
    }

    protected Chat(Parcel in) {
        id = in.readString();
        tMensajes = in.readString();
        horaMensaje = in.readLong();
        pos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(tMensajes);
        dest.writeLong(horaMensaje);
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMensaje() { return tMensajes; }
    public void setMensaje(String mensajeTexto) { this.tMensajes = mensajeTexto; }

    public Long getHoraMensaje() { return horaMensaje; }
    public void setHoraMensaje(Long horaMensaje) { this.horaMensaje = horaMensaje; }

    public int getPos() { return pos; }
    public void setPos(int pos) { this.pos = pos; }

}
