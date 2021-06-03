package com.example.diechichat.modelo;

public class FiltroAlimentos {
    private int tipo;
    private Cliente cliente;

    /* Constructor ********************************************************************************/

    public FiltroAlimentos(int tipo, Cliente cliente) {
        this.tipo = tipo;
        this.cliente = cliente;
    }

    /** Getters & Setters ***********************************************/

    public int getTipo() {
        return tipo;
    }
//    OP_DESAYUNO = 0;
//    OP_COMIDA = 1;
//    OP_CENA = 2;
//    OP_OTROS = 3;

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
