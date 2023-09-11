package main.java.com.uefs.librarymanager.model;

import utils.statusEmprestimo;

import java.util.Date;
import java.util.Objects;

public class Emprestimo {
    private Date dataInicio;
    private Date dataFim;
    private String usuarioId;
    private String livroISBN;
    private int atraso;
    private statusEmprestimo status;
    private String id;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

