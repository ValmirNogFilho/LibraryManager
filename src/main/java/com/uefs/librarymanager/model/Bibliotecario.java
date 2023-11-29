package com.uefs.librarymanager.model;

import com.uefs.librarymanager.model.Usuario;

/**
 * Esta classe é uma extensão da classe "Usuario", ela é responsável por registrar um bibliotecário no sistema contando com
 * algumas informações, tais como:
 * Nome do bibliotecário;
 * Endereço do bibliotecário;
 * Telefone do bibliotecário;
 * Senha.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 */
public class Bibliotecario extends Usuario {
    public Bibliotecario(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
    }

}
