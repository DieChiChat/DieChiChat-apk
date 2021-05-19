package com.example.diechichat.modelo;

public class Chat {
    private int id;
    private String mensajeTexto;
    private String mensajeUsuario;
    private String horaMensaje;

    public Chat() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMensajeTexto() { return mensajeTexto; }
    public void setMensajeTexto(String mensajeTexto) { this.mensajeTexto = mensajeTexto; }

    public String getMensajeUsuario() { return mensajeUsuario; }
    public void setMensajeUsuario(String mensajeUsuario) { this.mensajeUsuario = mensajeUsuario; }

    public String getHoraMensaje() { return horaMensaje; }
    public void setHoraMensaje(String horaMensaje) { this.horaMensaje = horaMensaje; }
}
