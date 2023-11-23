package com.uefs.librarymanager.dao.livro;

import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.model.Livro;

import java.util.*;
import java.util.stream.Collectors;

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
        throw new LivroException(LivroException.NAO_EXISTENTE);
    }

    @Override
    public List<Livro> findByTitulo(String titulo){
        List<Livro> livrosTitulo = new LinkedList<Livro>();
        for(Livro livro: findMany())
            if(livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                livrosTitulo.add(livro);
        return livrosTitulo;
    }



    @Override
    public List<Livro> findByCategoria(String categoria) {
        return findMany()
                .stream()
                .filter(l -> l.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }


    @Override
    public List<Livro> findByAutor(String autor) {
        return findMany()
                .stream()
                .filter(l -> l.getAutor().equals(autor))
                .collect(Collectors.toList());
    }



}
