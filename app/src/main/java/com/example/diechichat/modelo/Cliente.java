package com.example.diechichat.modelo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Cliente implements Parcelable {

    /* Atributos **********************************************************************************/
    private String id;
    private int idAdmin;             //pk
    @NonNull
    private String fechaNacimiento;           //pk
    @NonNull
    private String usuario;              //pk
    private String contrasena;     //not null
    private String nombreCompleto;         //not null
    private double peso;
    private double altura;
    private String nombre;
    private String apellidos;
    private Bitmap foto;
    private List<Alimento> tAlimentos;

    /* Constructor ********************************************************************************/
    public Cliente() {
    }


    /* Métodos Parcelable *************************************************************************/

    protected Cliente(Parcel in) {
        id = in.readString();
        idAdmin = in.readInt();
        fechaNacimiento = in.readString();
        usuario = in.readString();
        contrasena = in.readString();
        nombreCompleto = in.readString();
        peso = in.readDouble();
        altura = in.readDouble();
        nombre = in.readString();
        apellidos = in.readString();
        foto = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(idAdmin);
        dest.writeString(fechaNacimiento);
        dest.writeString(usuario);
        dest.writeString(contrasena);
        dest.writeString(nombreCompleto);
        dest.writeDouble(peso);
        dest.writeDouble(altura);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeParcelable(foto, flags);
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

    /* Métodos Getters&Setters ********************************************************************/

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
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(@NonNull String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getFechaFormat() { return fechaNacimiento.substring(6, 8) + "/" + fechaNacimiento.substring(4, 6) + "/" + fechaNacimiento.substring(0, 4); }
    public void setFechaFormat(@NonNull String fecha) { this.fechaNacimiento = fecha.substring(6, 10) + fecha.substring(3, 5) + fecha.substring(0, 2); }

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

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombre) { this.nombreCompleto = nombre; }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public Bitmap getFoto() { return foto; }
    public void setFoto(Bitmap foto) { this.foto = foto; }

}