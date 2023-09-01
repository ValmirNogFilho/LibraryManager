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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
