package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Alimento implements Parcelable {
    /* Atributos **********************************************************************************/
    private int id;
    private double kcal;
    private double carbohidratos;
    private double grasa;
    private double grasasSaturadas;
    private double colesterol;
    private double sodio;
    private double fibra;
    private double azucares;
    private double proteinas;
    private double calcio;
    private double hierro;
    private double potasio;
    private double vitaminaA;
    private double vitaminaC;
    private String nombre;

    /* Constructor ********************************************************************************/

    public Alimento() {
    }

    /* Métodos Parcelable *************************************************************************/

    protected Alimento(Parcel in) {
        id = in.readInt();
        carbohidratos = in.readDouble();
        grasa = in.readDouble();
        grasasSaturadas = in.readDouble();
        colesterol = in.readDouble();
        sodio = in.readDouble();
        fibra = in.readDouble();
        azucares = in.readDouble();
        proteinas = in.readDouble();
        calcio = in.readDouble();
        hierro = in.readDouble();
        potasio = in.readDouble();
        vitaminaA = in.readDouble();
        vitaminaC = in.readDouble();
        nombre = in.readString();
        kcal = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(carbohidratos);
        dest.writeDouble(grasa);
        dest.writeDouble(grasasSaturadas);
        dest.writeDouble(colesterol);
        dest.writeDouble(sodio);
        dest.writeDouble(fibra);
        dest.writeDouble(azucares);
        dest.writeDouble(proteinas);
        dest.writeDouble(calcio);
        dest.writeDouble(hierro);
        dest.writeDouble(potasio);
        dest.writeDouble(vitaminaA);
        dest.writeDouble(vitaminaC);
        dest.writeString(nombre);
        dest.writeDouble(kcal);
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

    public double getGrasasSaturadas() { return grasasSaturadas; }
    public void setGrasasSaturadas(double grasasSaturadas) { this.grasasSaturadas = grasasSaturadas; }

    public double getColesterol() { return colesterol; }
    public void setColesterol(double colesterol) { this.colesterol = colesterol; }

    public double getSodio() { return sodio; }
    public void setSodio(double sodio) { this.sodio = sodio; }

    public double getFibra() { return fibra; }
    public void setFibra(double fibra) { this.fibra = fibra; }

    public double getAzucares() { return azucares; }
    public void setAzucares(double azucares) { this.azucares = azucares; }

    public double getProteinas() { return proteinas; }
    public void setProteinas(double proteinas) { this.proteinas = proteinas; }

    public double getCalcio() { return calcio; }
    public void setCalcio(double calcio) { this.calcio = calcio; }

    public double getHierro() { return hierro; }
    public void setHierro(double hierro) { this.hierro = hierro; }

    public double getPotasio() { return potasio; }
    public void setPotasio(double potasio) { this.potasio = potasio; }

    public double getVitaminaA() { return vitaminaA; }
    public void setVitaminaA(double vitaminaA) { this.vitaminaA = vitaminaA; }

    public double getVitaminaC() { return vitaminaC; }
    public void setVitaminaC(double vitaminaC) { this.vitaminaC = vitaminaC; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCarbohidratos() { return carbohidratos; }
    public void setCarbohidratos(double carbohidratos) { this.carbohidratos = carbohidratos; }

    public double getKcal() { return kcal; }
    public void setKcal(double kcal) { this.kcal = kcal; }
}
