package main.java.com.uefs.librarymanager.dao.livro;

import main.java.com.uefs.librarymanager.exceptions.AutorException;
import main.java.com.uefs.librarymanager.exceptions.CategoriaException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
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
        addLivroEmCategoria(obj);
        addLivroEmAutor(obj);
        return obj;
    }

    @Override
    public void delete(Livro obj) {
        livros.remove(obj.getISBN());
        LinkedList<String> exemplares = isbnPorCategorias.get(obj.getCategoria());
        if(exemplares != null){
            exemplares.remove(obj);
            isbnPorCategorias.put(obj.getCategoria(), exemplares);
        }

        exemplares = isbnPorAutores.get(obj.getCategoria());
        if(exemplares != null){
            exemplares.remove(obj);
            isbnPorCategorias.put(obj.getAutor(), exemplares);
        }

    }

    @Override
    public void deleteMany() {
        livros = new HashMap<String, Livro>();
        isbnPorAutores = new HashMap<String, LinkedList<String>>();
        isbnPorCategorias = new HashMap<String, LinkedList<String>>();
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
    public Livro findByISBN(String ISBN) throws LivroException {
        Livro l = findByPrimaryKey(ISBN);
        if (l != null)
            return l;
        else throw new LivroException(LivroException.NAO_EXISTENTE);
    }

    @Override
    public void addCategoria(String categoria) {
        LinkedList<String> ISBNs = new LinkedList<String>();
        isbnPorCategorias.put(categoria, ISBNs);
    }

    @Override
    public Livro addLivroEmCategoria(Livro obj) {
        String categoria = obj.getCategoria();

        if(isbnPorCategorias.get(categoria) != null)
            isbnPorCategorias.get(categoria).add(obj.getISBN());
        else {
            LinkedList<String> isbns = new LinkedList<>();
            isbns.add(obj.getISBN());
            isbnPorCategorias.put(categoria, isbns);
        }

        return obj;
    }
    @Override
    public void removerLivroDeCategoria(Livro obj, String categoria) {
        LinkedList<String> isbns = isbnPorCategorias.get(categoria);
        if(isbns != null) {
            for (String i : isbns) {
                if (i.equals(obj.getISBN())) {
                    int index = isbns.indexOf(i);
                    if (index != -1) {
                        isbns.remove(index);
                        return;
                    }
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
    public Livro addLivroEmAutor(Livro obj) {
        String autor = obj.getAutor();

        if(isbnPorAutores.get(autor) != null)
            isbnPorAutores.get(autor).add(obj.getISBN());
        else {
            LinkedList<String> isbns = new LinkedList<>();
            isbns.add(obj.getISBN());
            isbnPorAutores.put(autor, isbns);
        }

        return obj;
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
