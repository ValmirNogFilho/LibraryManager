package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.usuario.SenhaInvalidaException;
import utils.IDGenerator;

public abstract class Usuario {
    private String nome;
    private String endereco;
    private String telefone;
    private String id;
    private String senha;

    public Usuario(String nome, String endereco, String telefone, String senha) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.senha = senha;
        id = IDGenerator.geraID();
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

    public void setSenha(String senha) throws SenhaInvalidaException {
        boolean permitido = (senha != null && senha.matches("[0-9]+") && senha.length() == 4);
        if (permitido)
            this.senha = senha;
        else
            throw new SenhaInvalidaException("Senha inválida!");
    }

}
