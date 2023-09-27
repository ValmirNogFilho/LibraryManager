package main.java.com.uefs.librarymanager.model;

import utils.IDGenerator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class Reserva {
    private String idUsuario;
    private LocalDate dataFim;
    private int id;
    private String ISBN;

    public Reserva(String idUsuario, String isbn) {
        this.idUsuario = idUsuario;
        this.dataFim = null;
        this.ISBN = isbn;
        this.id = 0;
    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
