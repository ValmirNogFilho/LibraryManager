package com.uefs.librarymanager.exceptions;



public class EmprestimoException extends Exception {
    public static final String LIMITE_RENOVACOES = "Limite de reservas excedido.";
    public EmprestimoException(String message) {
        super(message);
    }
}
