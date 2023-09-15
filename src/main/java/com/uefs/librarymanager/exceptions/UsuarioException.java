package main.java.com.uefs.librarymanager.exceptions;

import main.java.com.uefs.librarymanager.model.Usuario;


public class UsuarioException extends Exception{
    public static final String LIMITE_EMPRESTIMOS = "Leitor não pode fazer mais do que três empréstimos";
    public static final String LIMITE_RESERVAS = "Leitor não pode fazer mais do que duas reservas";
    public UsuarioException(String message) {
        super(message);
    }


}
