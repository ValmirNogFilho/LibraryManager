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
    /**
     * Este método adiciona uma categoria ao livro
     * @param categoria
     */
    public void addCategoria(String categoria);

    /**
     * Este método adiciona o livro de dada categoria a um acervo da própria catégoria
     * @param obj
     * @return obj
     */
    public Livro addLivroEmCategoria(Livro obj);

    /**
     * Este método remove o livro de dada categoria do acervo da própria categoria
     * @param obj
     * @param categoria
     */
    public void removerLivroDeCategoria(Livro obj, String categoria);

    /**
     * Este método adiciona um livro à uma LinkedList de dada categoria.
     * @param categoria
     * @return todos os livros da categoria
     */
    public List<Livro> findByCategoria(String categoria);

    /**
     * Este método adiciona um livro a um acervo de ISBN por um dado autor específico.
     * @param autor
     */
    public void addAutor(String autor);

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
     * Este método adiciona dado livro numa espécie de acervo de livros de um mesmo autor por meio de uma LinkedList.
     * @param obj
     * @return obj
     */
    public Livro addLivroEmAutor(Livro obj);

    /**
     * Este método remove um dado livro do "acervo" de livros de um determinado autor.
     * @param obj
     * @param autor
     */
    public void removerLivroDeAutor(Livro obj, String autor);

    /**
     * Este método faz a busca de um livro pelo seu "Primarykey" que neste caaso é o ISBN do livro.
     * Caso o ISBN não exista, uma exceção é ativada.
     * @param ISBN
     * @return um livro l correspondente ao ISBN ou uma exceção caso o livro não exista.
     * @throws LivroException
     */
    public Livro findByISBN(String ISBN) throws LivroException;
}
