package main.java.com.uefs.librarymanager.dao.livro;
import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;

import java.util.List;

/**
 * Esta interface contém métodos que correspondem às informações do livro, como:
 * Adicionar categoria;
 * Adicionar o livro em uma categoria;
 * Remover livro d euma categoria;
 * Adicionar um autor no livro;
 * Remover o autor.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.dao.CRUD
 * @see main.java.com.uefs.librarymanager.exceptions.LivroException
 * @see main.java.com.uefs.librarymanager.model.Livro
 * @see java.util.List
 *
 */
public interface LivroDAO extends CRUD<Livro> {

    public List<Livro> findByCategoria(String categoria);

    /**
     * Este método adiciona dado livro à uma LinkedList de um dado autor, formando uma espécie de acervo por autor, todos
     * os livros de um mesmo autor agrupados num só lugar.
     * @param autor
     * @return todos os livros do autor.
     */
    public List<Livro> findByAutor(String autor);

    /**
     * Este método agrupa livros de mesmo título numa lista
     * @param titulo
     * @return lista com livros de mesmo título.
     */
    public List<Livro> findByTitulo(String titulo);


    /**
     * Este método faz a busca de um livro pelo seu "Primarykey" que neste caaso é o ISBN do livro.
     * Caso o ISBN não exista, uma exceção é ativada.
     * @param ISBN
     * @return um livro l correspondente ao ISBN ou uma exceção caso o livro não exista.
     * @throws LivroException
     */
    public Livro findByISBN(String ISBN) throws LivroException;
}
