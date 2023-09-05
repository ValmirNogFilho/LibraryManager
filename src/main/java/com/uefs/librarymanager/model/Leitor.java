package main.java.com.uefs.librarymanager.model;

import utils.statusLeitor;

import java.util.Date;

public class Leitor extends Usuario{

    private Date inicioMulta;
    private int prazoMulta;
    private int numerosEmprestimo;
    private int numeroReservas;
    private statusLeitor status;

    public Leitor(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
        this.inicioMulta = null;
        this.prazoMulta = 0;
        this.numerosEmprestimo = 0;
        this.numeroReservas = 0;
        this.status = statusLeitor.LIVRE;
    }

    public Date getInicioMulta() {
        return inicioMulta;
    }

    public void setInicioMulta(Date inicioMulta) {
        this.inicioMulta = inicioMulta;
    }

    public int getPrazoMulta() {
        return prazoMulta;
    }

    public void setPrazoMulta(int prazoMulta) {
        this.prazoMulta = prazoMulta;
    }

    public int getNumerosEmprestimo() {
        return numerosEmprestimo;
    }

    public void setNumerosEmprestimo(int numerosEmprestimo) {
        this.numerosEmprestimo = numerosEmprestimo;
    }

    public int getNumeroReservas() {
        return numeroReservas;
    }

    public void setNumeroReservas(int numeroReservas) {
        this.numeroReservas = numeroReservas;
    }

    public statusLeitor getStatus() {
        return status;
    }

    public void setStatus(statusLeitor status) {
        this.status = status;
    }


}
