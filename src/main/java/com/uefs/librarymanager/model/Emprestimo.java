package main.java.com.uefs.librarymanager.model;

import utils.IDGenerator;
import utils.statusEmprestimo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Emprestimo {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String usuarioId;
    private String livroISBN;
    private int atraso;
    private statusEmprestimo status;
    private int id;

    public Emprestimo(LocalDate dataInicio, LocalDate dataFim, String usuarioId, String livroISBN) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuarioId = usuarioId;
        this.livroISBN = livroISBN;
        this.atraso = 0;
        this.status = statusEmprestimo.ANDAMENTO;
        this.id = 0;
    }

    public int proximoID(){
        return id++;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getLivroISBN() {
        return livroISBN;
    }

    public void setLivroISBN(String livroISBN) {
        this.livroISBN = livroISBN;
    }

    public int getAtraso() {
        return atraso;
    }

    public void setAtraso(int atraso) {
        this.atraso = atraso;
    }

    public statusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(statusEmprestimo status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(livroISBN, that.livroISBN) && Objects.equals(id, that.id);
    }


}

