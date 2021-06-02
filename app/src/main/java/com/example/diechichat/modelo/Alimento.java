package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Alimento implements Parcelable {
    /* Atributos **********************************************************************************/
    private int id;
    private String cantidad;
    private double kcal;
    private double proteinas;
    private double grasa;
    private double carbohidratos;
    private double fibra;
    private String nombre;

    /* Constructor ********************************************************************************/

    public Alimento() {
    }

    /* Métodos Parcelable *************************************************************************/

    protected Alimento(Parcel in) {
        id = in.readInt();
        carbohidratos = in.readDouble();
        grasa = in.readDouble();
        fibra = in.readDouble();
        proteinas = in.readDouble();
        nombre = in.readString();
        kcal = in.readDouble();
        cantidad = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(carbohidratos);
        dest.writeDouble(grasa);
        dest.writeDouble(fibra);
        dest.writeDouble(proteinas);
        dest.writeString(nombre);
        dest.writeDouble(kcal);
        dest.writeString(cantidad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Alimento> CREATOR = new Creator<Alimento>() {
        @Override
        public Alimento createFromParcel(Parcel in) {
            return new Alimento(in);
        }

        @Override
        public Alimento[] newArray(int size) {
            return new Alimento[size];
        }
    };

    /** Getters & Setters ***********************************************/

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getGrasa() { return grasa; }
    public void setGrasa(double grasa) { this.grasa = grasa; }

    public double getFibra() { return fibra; }
    public void setFibra(double fibra) { this.fibra = fibra; }

    public double getProteinas() { return proteinas; }
    public void setProteinas(double proteinas) { this.proteinas = proteinas; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCarbohidratos() { return carbohidratos; }
    public void setCarbohidratos(double carbohidratos) { this.carbohidratos = carbohidratos; }

    public double getKcal() { return kcal; }
    public void setKcal(double kcal) { this.kcal = kcal; }

    public void setCantidad(String cantidad) { this.cantidad = cantidad; }
    public String getCantidad() { return cantidad; }


}
