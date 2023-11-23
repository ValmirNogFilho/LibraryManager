package com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.dao.CRUD;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;

/**
 * Esta interface é responsável por conter um método que diz respeito ao "OperadorDAO". Um operador pode ser um
 * administrador ou um bibliotecário.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.dao.CRUD
 * @see UsuarioException
 * @see Usuario
 */
public interface OperadorDAO extends CRUD<Usuario> {
    /**
     * Este método é responsável por tentar localizar um operador (administrador ou bibliotecário) a partir de um objeto
     * de identificação que neste caso é o ID. Caso, seja encontrado, é retornado um operador "o", caso não, umma exceção
     * é ativada.
     * @param id
     * @return um operador "o" ou uma exceção.
     * @throws UsuarioException
     */
    public Usuario findById(String id) throws UsuarioException;
}
