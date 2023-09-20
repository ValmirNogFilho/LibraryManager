package main.java.com.uefs.librarymanager.exceptions;

import main.java.com.uefs.librarymanager.model.Usuario;


public class UsuarioException extends Exception{
    public static final String LIMITE_EMPRESTIMOS = "Leitor não pode fazer mais do que três empréstimos";
    public static final String LIMITE_RESERVAS = "Leitor não pode fazer mais do que duas reservas";
    public static final String NAO_EXISTENTE = "Leitor não encontrado";
    public static final String USUARIO_MULTADO = "Leitor multado";
    public static final String USUARIO_BLOQUEADO = "Leitor bloqueado";
    public UsuarioException(String message) {
        super(message);
    }


}
