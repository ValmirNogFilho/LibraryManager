package main.java.com.uefs.librarymanager.model;

import utils.statusEmprestimo;

import java.util.Date;

public class Emprestimo {
    private Date dataInicio;
    private Date dataFim;
    private String usuarioId;
    private String livroISBN;
    private int atraso;
    private statusEmprestimo status;
    private int id;

}
