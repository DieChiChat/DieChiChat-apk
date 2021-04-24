package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Cliente implements Parcelable {
    private String id;
    private int idAdmin;             //pk
    @NonNull
    private String fechaNacimiento;           //pk
    @NonNull
    private String usuario;              //pk
    private String contrasena;     //not null
    private String Nombre;         //not null
    private String Apellidos;      //not null

    public Cliente() {
    }


    protected Cliente(Parcel in) {
        idAdmin = in.readInt();
        fechaNacimiento = in.readString();
        usuario = in.readString();
        contrasena = in.readString();
        Nombre = in.readString();
        Apellidos = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAdmin);
        dest.writeString(fechaNacimiento);
        dest.writeString(usuario);
        dest.writeString(contrasena);
        dest.writeString(Nombre);
        dest.writeString(Apellidos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @NonNull
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NonNull String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @NonNull
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(@NonNull String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }
}