package main.java.com.uefs.librarymanager.model;

import java.util.PriorityQueue;
import java.util.Queue;

public class Reserva {
    private String idUsuario;
    private int prazo;
    private int id;

    public Reserva(String idUsuario, int prazo, int id) {
        this.idUsuario = idUsuario;
        this.prazo = prazo;
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
