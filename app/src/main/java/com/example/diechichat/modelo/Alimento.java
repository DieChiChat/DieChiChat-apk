package com.example.diechichat.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Alimento implements Parcelable {
    /* Atributos **********************************************************************************/
    private int id;
    private double grasaSaturada;
    private double colesterol;
    private double sodio;
    private double fibraDietetica;
    private double azucares;
    private double proteinas;
    private double calcio;
    private double alcoholes;
    private double hierro;
    private double potasio;
    private double vitaminaA;
    private double vitaminaC;

    /* Constructor ********************************************************************************/

    public Alimento() {
    }

    /* Métodos Parcelable *************************************************************************/

    protected Alimento(Parcel in) {
        id = in.readInt();
        grasaSaturada = in.readDouble();
        colesterol = in.readDouble();
        sodio = in.readDouble();
        fibraDietetica = in.readDouble();
        azucares = in.readDouble();
        proteinas = in.readDouble();
        calcio = in.readDouble();
        alcoholes = in.readDouble();
        hierro = in.readDouble();
        potasio = in.readDouble();
        vitaminaA = in.readDouble();
        vitaminaC = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(grasaSaturada);
        dest.writeDouble(colesterol);
        dest.writeDouble(sodio);
        dest.writeDouble(fibraDietetica);
        dest.writeDouble(azucares);
        dest.writeDouble(proteinas);
        dest.writeDouble(calcio);
        dest.writeDouble(alcoholes);
        dest.writeDouble(hierro);
        dest.writeDouble(potasio);
        dest.writeDouble(vitaminaA);
        dest.writeDouble(vitaminaC);
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

    public double getGrasaSaturada() { return grasaSaturada; }
    public void setGrasaSaturada(double grasaSaturada) { this.grasaSaturada = grasaSaturada; }

    public double getColesterol() { return colesterol; }
    public void setColesterol(double colesterol) { this.colesterol = colesterol; }

    public double getSodio() { return sodio; }
    public void setSodio(double sodio) { this.sodio = sodio; }

    public double getFibraDietetica() { return fibraDietetica; }
    public void setFibraDietetica(double fibraDietetica) { this.fibraDietetica = fibraDietetica; }

    public double getAzucares() { return azucares; }
    public void setAzucares(double azucares) { this.azucares = azucares; }

    public double getProteinas() { return proteinas; }
    public void setProteinas(double proteinas) { this.proteinas = proteinas; }

    public double getCalcio() { return calcio; }
    public void setCalcio(double calcio) { this.calcio = calcio; }

    public double getAlcoholes() { return alcoholes; }
    public void setAlcoholes(double alcoholes) { this.alcoholes = alcoholes; }

    public double getHierro() { return hierro; }
    public void setHierro(double hierro) { this.hierro = hierro; }

    public double getPotasio() { return potasio; }
    public void setPotasio(double potasio) { this.potasio = potasio; }

    public double getVitaminaA() { return vitaminaA; }
    public void setVitaminaA(double vitaminaA) { this.vitaminaA = vitaminaA; }

    public double getVitaminaC() { return vitaminaC; }
    public void setVitaminaC(double vitaminaC) { this.vitaminaC = vitaminaC; }
}
