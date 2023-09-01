package main.java.com.uefs.librarymanager.model;

public abstract class Bibliotecario extends Usuario {
    public Bibliotecario(String nome, String endereco, String telefone, String id, String senha) {
        super(nome, endereco, telefone, id, senha);
    }
}
