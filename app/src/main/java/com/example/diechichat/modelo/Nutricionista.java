package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Nutricionista implements Parcelable {

    /* Atributos **********************************************************************************/

    private int id;                 // PK
    private String nombre;
    private String apellidos;
    private String usuario;
    private String contrasena;


    /* Constructor ********************************************************************************/
    public Nutricionista() {

    }

    /* Métodos Parcelable *************************************************************************/

    protected Nutricionista(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        apellidos = in.readString();
        usuario = in.readString();
        contrasena = in.readString();
    }

    public static final Creator<Nutricionista> CREATOR = new Creator<Nutricionista>() {
        @Override
        public Nutricionista createFromParcel(Parcel in) {
            return new Nutricionista(in);
        }

        @Override
        public Nutricionista[] newArray(int size) {
            return new Nutricionista[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(usuario);
        dest.writeString(contrasena);
    }

    /* Métodos Getters&Setters ********************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apillidos) {
        this.apellidos = apillidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

}
