package main.java.com.uefs.librarymanager.model;

public abstract class Administrador extends Usuario {
    public Administrador(String nome, String endereco, String telefone, String id, String senha) {
        super(nome, endereco, telefone, id, senha);
    }
}
