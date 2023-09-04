package main.java.com.uefs.librarymanager.model;

import utils.statusLeitor;

import java.util.Date;

public class Leitor extends Usuario{

    private Date inicioMulta;
    private int prazoMulta;
    private int numerosEmprestimo;
    private int numeroReservas;
    private statusLeitor status;

    public Leitor(String nome, String endereco, String telefone, Date inicioMulta, Integer prazoMulta, int numerosEmprestimo, int numeroReservas, statusLeitor status) {
        super(nome, endereco, telefone, null);
        this.inicioMulta = inicioMulta;
        this.prazoMulta = prazoMulta;
        this.numerosEmprestimo = numerosEmprestimo;
        this.numeroReservas = numeroReservas;
        this.status = status;
    }


}
