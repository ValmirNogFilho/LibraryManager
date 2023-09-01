package main.java.com.uefs.librarymanager.model;

import utils.statusLeitor;

import java.util.Date;

public class Leitor extends Usuario{
    public Leitor(String _nome, String _endereco, String _telefone, String _id, String _senha){
        super(_nome,_endereco, _telefone, _id, _senha);
    }
    private Date inicioMulta;
    private int prazoMulta;
    private int numerosEmprestimo;
    private int numeroReservas;
    private statusLeitor status;

}
