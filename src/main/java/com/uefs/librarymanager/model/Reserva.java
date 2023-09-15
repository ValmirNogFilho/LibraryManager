package main.java.com.uefs.librarymanager.model;

import utils.IDGenerator;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class Reserva {
    private String idUsuario;
    private int prazo;
    private String id;
    private String ISBN;

    public Reserva(String idUsuario, int prazo, String isbn) {
        this.idUsuario = idUsuario;
        this.prazo = prazo;
        this.ISBN = isbn;
        this.id = IDGenerator.geraID();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id) && Objects.equals(ISBN, reserva.ISBN);
    }

}
