package main.java.com.uefs.librarymanager.model;

public abstract class Usuario {
    private String nome;
    private String endereco;
    private String telefone;
    private String id;
    private String senha;

    public Usuario(String nome, String endereco, String telefone, String id, String senha) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.senha = senha;
    }
}
