package com.uefs.librarymanager.exceptions;



public class EmprestimoException extends Exception {
    public static final String LIMITE_RENOVACOES = "Limite de renovações excedido.";
    public static final String USUARIO_AINDA_TEM_EMPRESTIMOS = "Usuario ainda tem empréstimos em andamento";
    public EmprestimoException(String message) {
        super(message);
    }
}
