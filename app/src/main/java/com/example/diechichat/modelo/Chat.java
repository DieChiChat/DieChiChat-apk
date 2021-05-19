package com.example.diechichat.modelo;

import java.util.Date;

public class Chat {
    private int id;
    private String mensajeTexto;
    private String mensajeUsuario;
    private long horaMensaje;

    public Chat() {
        this.horaMensaje = new Date().getTime();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMensajeTexto() { return mensajeTexto; }
    public void setMensajeTexto(String mensajeTexto) { this.mensajeTexto = mensajeTexto; }

    public String getMensajeUsuario() { return mensajeUsuario; }
    public void setMensajeUsuario(String mensajeUsuario) { this.mensajeUsuario = mensajeUsuario; }

    public Long getHoraMensaje() { return horaMensaje; }
    public void setHoraMensaje(Long horaMensaje) { this.horaMensaje = horaMensaje; }
}
