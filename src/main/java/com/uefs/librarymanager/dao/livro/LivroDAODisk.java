package com.uefs.librarymanager.dao.livro;

import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.utils.FileBehaviour;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class LivroDAODisk implements LivroDAO{

    File arquivo;
    private static final String NOMEARQUIVO= "livros";
  
    public LivroDAODisk(){
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    @Override
    public Livro create(Livro obj) {
        Map<String, Livro> livros = findManyMap();
        livros.put(obj.getISBN(), obj);
        FileBehaviour.sobreescreverArquivo(arquivo, livros);

        return obj;
    }

    @Override
    public void delete(Livro obj) {
        Map<String, Livro> livros = findManyMap();
        livros.remove(obj.getISBN());

        FileBehaviour.sobreescreverArquivo(arquivo, livros);

    }


    @Override
    public void deleteMany() {
        FileBehaviour.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Livro update(Livro obj) {
        Map<String, Livro> livros = findManyMap();

        livros.remove(obj.getISBN());
        livros.put(obj.getISBN(), obj);

        FileBehaviour.sobreescreverArquivo(arquivo, livros);

        return obj;
    }

    @Override
    public List<Livro> findMany() {
        Collection<Livro> values = findManyMap().values();
        return new LinkedList<Livro>(values);
    }

    @Override
    public Livro findByPrimaryKey(String ISBN) {
        return findManyMap().get(ISBN);
    }

    private List<Livro> findBooksBy(String identificador){
        return findMany()
                .stream()
                .filter(l -> l.getCategoria().equals(identificador))
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findBooksByCategoria(String categoria) {
        return findBooksBy(categoria);
    }

    @Override
    public List<Livro> findBooksByAutor(String autor) {
        return findBooksBy(autor);
    }

    @Override
    public List<Livro> findBooksByTitulo(String titulo) {
        return findMany()
                .stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

   
    @Override
    public Livro findByISBN(String ISBN) throws LivroException {
        Livro l = findByPrimaryKey(ISBN);
        if (l != null)
            return l;
        throw new LivroException(LivroException.NAO_EXISTENTE);
    }

    private Map<String, Livro> findManyMap(){
        return FileBehaviour.consultarArquivo(arquivo);
    }
    

}
