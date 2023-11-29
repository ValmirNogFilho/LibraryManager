package com.uefs.librarymanager.dao.usuario.leitor;

import com.uefs.librarymanager.dao.CRUD;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
/**
 * Esta interface é responsável por conter um método que diz respeito ao "LeitorDAO". Um Leitor é o tipo de usuário que
 * faz empréstimos de livros na biblioteca, pode entrar na fila de reserva e está sujeito à multa caso atrase a devolução
 * de algum livro.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see CRUD
 * @see UsuarioException
 * @see Leitor
 */

public interface LeitorDAO extends CRUD<Leitor> {
    /**
     * Este método é responsável por tentar localizar um leitor a partir de um objeto de identificação que neste caso é
     * o ID. Caso, seja encontrado, é retornado um leitor "l", caso não, umma exceção é ativada.
     * @param id
     * @return leitor "l" ou uma exceção.
     * @throws UsuarioException
     */
    public Leitor findById(String id) throws UsuarioException;

}
