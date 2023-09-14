package main.java.com.uefs.librarymanager.exceptions;

import main.java.com.uefs.librarymanager.model.Usuario;


public class UsuarioException extends Exception{
    public static final String LIMITE_EMPRESTIMOS = "Leitor não pode fazer mais do que três empréstimos";
    public UsuarioException(String message) {
        super(message);
    }


}
