package main.java.com.uefs.librarymanager.dao.livro;
import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;

import java.util.List;

public interface LivroDAO extends CRUD<Livro> {

    public void addCategoria(String categoria);

    public Livro addLivroEmCategoria(Livro obj);

    public void removerLivroDeCategoria(Livro obj, String categoria);

    public List<Livro> findByCategoria(String categoria);

    public void addAutor(String autor);

    public List<Livro> findByAutor(String autor);

    public Livro addLivroEmAutor(Livro obj);

    public void removerLivroDeAutor(Livro obj, String autor);

    public Livro findByISBN(String ISBN) throws LivroException;
}
