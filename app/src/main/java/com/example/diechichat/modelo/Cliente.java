package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Cliente implements Parcelable {
    private String id;
    private int idAdmin;             //pk
    @NonNull
    private int edad;           //pk
    @NonNull
    private String usuario;              //pk
    private String contrasena;     //not null
    private String nombre;         //not null
    private String apellidos;      //not null
    private double peso;
    private double altura;

    public Cliente() {
    }


    protected Cliente(Parcel in) {
        idAdmin = in.readInt();
        edad = in.readInt();
        usuario = in.readString();
        contrasena = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        peso = in.readDouble();
        altura = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAdmin);
        dest.writeInt(edad);
        dest.writeString(usuario);
        dest.writeString(contrasena);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeDouble(peso);
        dest.writeDouble(altura);
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
    public int getEdad() { return edad; }
    public void setEdad(@NonNull int edad) { this.edad = edad; }

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
        return nombre;
    }
    public void setNombre(String nombre) {
        nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        apellidos = apellidos;
    }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

}