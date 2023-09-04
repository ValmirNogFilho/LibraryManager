package main.java.com.uefs.librarymanager.dao.usuario;

public class SenhaInvalidaException extends Exception{
    public SenhaInvalidaException(String mensagem){
        super(mensagem);
    }
}
