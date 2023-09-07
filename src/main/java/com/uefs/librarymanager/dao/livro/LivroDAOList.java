package main.java.com.uefs.librarymanager.dao.livro;

import main.java.com.uefs.librarymanager.exceptions.AutorException;
import main.java.com.uefs.librarymanager.exceptions.CategoriaException;
import main.java.com.uefs.librarymanager.model.Livro;

import java.util.*;

public class LivroDAOList implements LivroDAO{
    Map<String, Livro> livros;
    HashMap<String, LinkedList<String>> isbnPorCategorias;
    Map<String, LinkedList<String>> isbnPorAutores;
    public LivroDAOList() {
        livros = new HashMap<String, Livro>();
        isbnPorCategorias = new HashMap<String, LinkedList<String>>();
        isbnPorAutores = new HashMap<String, LinkedList<String>>();
    }

    @Override
    public Livro create(Livro obj) {
        livros.put(obj.getISBN(), obj);
        return obj;
    }

    @Override
    public void delete(Livro obj) {
        livros.remove(obj.getISBN());
    }

    @Override
    public void deleteMany() {
        livros = new HashMap<String, Livro>();
    }

    @Override
    public Livro update(Livro obj) {
        livros.remove(obj.getISBN());
        livros.put(obj.getISBN(), obj);
        return obj;
    }

    @Override
    public List<Livro> findMany() {
        Collection<Livro> values = livros.values();
        return new LinkedList<Livro>(values);
    }

    @Override
    public Livro findByPrimaryKey(String ISBN) {
        return livros.get(ISBN);
    }

    @Override
    public void addCategoria(String categoria) {
        LinkedList<String> ISBNs = new LinkedList<String>();
        isbnPorCategorias.put(categoria, ISBNs);
    }

    @Override
    public Livro addLivroEmCategoria(Livro obj) throws CategoriaException {
        String categoria = obj.getCategoria();
        LinkedList<String> isbns = isbnPorCategorias.get(categoria);
        if (isbns != null) {
            isbns.add(obj.getISBN());
            isbnPorCategorias.put(categoria, isbns);
            return obj;
        }
        else
            throw new CategoriaException("Categoria não encontrada.");
    }

    @Override
    public void removerLivroDeCategoria(Livro obj, String categoria) {
        LinkedList<String> isbns = isbnPorCategorias.get(categoria);
        if(isbns != null) {
            for (String i : isbns) {
                if (i.equals(obj.getISBN())) {
                    int index = isbns.indexOf(i);
                    if (index != -1)
                        isbns.remove(index);
                }
            }
            isbnPorCategorias.put(categoria, isbns);
        }

    }

    @Override
    public List<Livro> findByCategoria(String categoria) {
        LinkedList<String> ISBNsDaCategoria = isbnPorCategorias.get(categoria);
        List<Livro> livrosDaCategoria = new LinkedList<Livro>();
        for(String isbn: ISBNsDaCategoria){
            Livro livro = livros.get(isbn);
            livrosDaCategoria.add(livro);
        }
        return livrosDaCategoria;
    }

    @Override
    public void addAutor(String autor) {
        LinkedList<String> ISBNs = new LinkedList<String>();
        isbnPorAutores.put(autor, ISBNs);
    }

    @Override
    public List<Livro> findByAutor(String autor) {
        LinkedList<String> ISBNsDoAutor = isbnPorAutores.get(autor);
        List<Livro> livrosDoAutor = new LinkedList<Livro>();
        for(String isbn: ISBNsDoAutor){
            Livro livro = livros.get(isbn);
            livrosDoAutor.add(livro);
        }
        return livrosDoAutor;
    }

    @Override
    public Livro addLivroEmAutor(Livro obj) throws AutorException {
        String autor = obj.getAutor();
        LinkedList<String> isbns = isbnPorAutores.get(autor);
        if (isbns != null) {
            isbns.add(obj.getISBN());
            isbnPorAutores.put(autor, isbns);
            return obj;
        }
        else
            throw new AutorException("Autor não encontrado.");
    }

    @Override
    public void removerLivroDeAutor(Livro obj, String autor) {
        LinkedList<String> isbns = isbnPorAutores.get(autor);
        if(isbns != null) {
            for (String i : isbns) {
                if (i.equals(obj.getISBN())) {
                    int index = isbns.indexOf(i);
                    if (index != -1)
                        isbns.remove(index);
                }
            }
            isbnPorAutores.put(autor, isbns);
        }
    }


}
