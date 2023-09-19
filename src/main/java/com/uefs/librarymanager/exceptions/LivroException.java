package main.java.com.uefs.librarymanager.exceptions;

public class LivroException extends Exception{
    public static final String FILA_NAO_VAZIA = "A fila de reservas desse livro não está vazia";
    public static final String LEITOR_TEM_ESSE_ISBN = "Leitor já fez empréstimo desse livro.";
    public static final String SEM_EXEMPLARES = "Não há exemplares disponíveis do livro";
    public static final String NAO_EXISTENTE = "Livro não encontrado.";
    public LivroException(String message) {
        super(message);
    }
}
