package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.IDGenerator;

import java.util.Objects;

public class Usuario {
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

    public void setSenha(String senha) throws UsuarioException {
        boolean permitido = (senha != null && senha.matches("[0-9]+") && senha.length() == 4);
        if (permitido)
            this.senha = senha;
        else
            throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

}
