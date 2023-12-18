package com.uefs.librarymanager.model;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.utils.IDGenerator;
import com.uefs.librarymanager.utils.cargoUsuario;

import java.io.Serializable;
import java.util.Objects;

/**
 * Esta classe contém dados gerais de um usuário do sistema tais como: Nome, endereço, telefone, id e senha.
 * Contando com verificação de validação de senha.
 * Isso possibilita que esta classe possa ser extendida para tipos específicos de usuários como:
 * Leitor;
 * Administrador;
 * Bibliotecário.
 * Exemplo do uso:
 * Usuario usuario = new Usuario ("nome", "endereco", "telefone", "senha")
 * Além disso, no momento do cadastro do usuário, é gerado um ID único para ele.
 * @author: Valmir Alves Nogueira Filho
 * @author: Kevin Cordeiro Borges
 * @see:java.util.objects
 * @see: main.java.com.uefs.librarymanager.utils.IDGenerator
 * @see: main.java.com.uefs.librarymanager.exceptions.UsuarioException
 *
 */
public class Usuario implements Serializable {
    private String nome;
    private String endereco;
    private String telefone;
    private String id;
    private String senha;
    private cargoUsuario cargo;

    public Usuario(String nome, String endereco, String telefone, String senha, cargoUsuario cargo) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.senha = senha;
        this.cargo = cargo;
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

    public cargoUsuario getCargo() {
        return cargo;
    }

    public void setCargo(cargoUsuario cargo) {
        this.cargo = cargo;
    }

    /**
     * Este método serve para verificar a validação da senha.
     * Uma senha só será válida se, e somente se ela não for vazia (null) e for composta por quatro caracters numéricos.
     * Caso a senha fuja de qualquer uma dessas duas regras, é posta em ação a exceção SENHA_INVALIDA.
     * @param senha
     * @throws UsuarioException
     */
    public void setSenha(String senha) throws UsuarioException {
        boolean permitido = (senha != null && senha.matches("[0-9]+") && senha.length() == 4);
        if (permitido)
            this.senha = senha;
        else
            throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
    }

    /**
     * Este método compara IDs de usuários, ele retorna valores booleanos para duas situações:
     * IDs iguais retorna True;
     * IDs distintos retorna False.
     * @param o
     * @return True or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", id='" + id + '\'' +
                ", senha='" + senha + '\'' +
                ", cargo=" + cargo +
                '}';
    }
}
