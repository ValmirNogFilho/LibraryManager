package main.java.com.uefs.librarymanager.dao.livro;

import main.java.com.uefs.librarymanager.model.Usuario;
import utils.FileBehaviour;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;

import java.io.*;
import java.util.*;

public class LivroDAOFile implements LivroDAO{

    File arquivo;
    File arquivoCategorias;
    File arquivoAutores;

    public LivroDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("livros");
        arquivoCategorias = FileBehaviour.gerarArquivo("categorias");
        arquivoAutores = FileBehaviour.gerarArquivo("autores");
    }

    @Override
    public Livro create(Livro obj) {
        Map<String, Livro> livros = findManyLivros();
        livros.put(obj.getISBN(), obj);

        replaceInFile(arquivo, livros);

        return obj;
    }

    @Override
    public void delete(Livro obj) {
        Map<String, Livro> livros = findManyLivros();
        livros.remove(obj.getISBN());

        Map <String, LinkedList<String>> isbnPorCategorias = findManyISBNCategorias();
        LinkedList<String> exemplares = isbnPorCategorias.get(obj.getCategoria());

        Map <String, LinkedList<String>> isbnPorAutores = findManyISBNAutores();

        if(exemplares != null){
            exemplares.remove(obj);
            isbnPorCategorias.put(obj.getCategoria(), exemplares);
        }

        exemplares = isbnPorAutores.get(obj.getCategoria());
        if(exemplares != null){
            exemplares.remove(obj);
            isbnPorCategorias.put(obj.getAutor(), exemplares);
        }

        replaceInFile(arquivo, livros);
        replaceInFile(arquivoCategorias, isbnPorCategorias);
        replaceInFile(arquivoAutores, isbnPorAutores);

    }


    @Override
    public void deleteMany() {
        try {
            new FileOutputStream(arquivo).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Livro update(Livro obj) {
        Map<String, Livro> livros = findManyLivros();

        livros.remove(obj.getISBN());
        livros.put(obj.getISBN(), obj);

        replaceInFile(arquivo, livros);

        return obj;
    }

    @Override
    public List<Livro> findMany() {
        Collection<Livro> values = findManyLivros().values();
        return new LinkedList<Livro>(values);
    }

    @Override
    public Livro findByPrimaryKey(String ISBN) {
        return findManyLivros().get(ISBN);
    }

    @Override
    public void addCategoria(String categoria) {
        LinkedList<String> ISBNs = new LinkedList<String>();
        Map<String, LinkedList<String>> isbnPorCategorias = findManyISBNCategorias();
        isbnPorCategorias.put(categoria, ISBNs);
        replaceInFile(arquivoCategorias, isbnPorCategorias);
    }

    @Override
    public Livro addLivroEmCategoria(Livro obj) {
        String categoria = obj.getCategoria();
        Map<String, LinkedList<String>> isbnPorCategorias = findManyISBNCategorias();

        if(isbnPorCategorias.get(categoria) == null)
            addCategoria(categoria);

        isbnPorCategorias.get(categoria).add(obj.getISBN());
        replaceInFile(arquivoCategorias, isbnPorCategorias);
        return obj;
    }

    @Override
    public void removerLivroDeCategoria(Livro obj, String categoria) {
        Map<String, LinkedList<String>> isbnPorCategorias = findManyISBNCategorias();
        LinkedList<String> isbns = isbnPorCategorias.get(categoria);
        isbns.remove(obj.getISBN());
        isbnPorCategorias.put(categoria, isbns);

        replaceInFile(arquivoCategorias, isbnPorCategorias);
    }

    @Override
    public List<Livro> findByCategoria(String categoria) {
        LinkedList<String> isbns = findManyISBNCategorias().get(categoria);
        List<Livro> livrosDaCategoria = new LinkedList<Livro>();

        Map<String, Livro> livros = findManyLivros();
        for(String isbn: isbns){
            Livro livro = livros.get(isbn);
            livrosDaCategoria.add(livro);
        }

        return livrosDaCategoria;
    }

    @Override
    public void addAutor(String autor) {
        LinkedList<String> ISBNs = new LinkedList<String>();
        Map<String, LinkedList<String>> isbnPorAutores = findManyISBNAutores();
        isbnPorAutores.put(autor, ISBNs);
        replaceInFile(arquivoAutores, isbnPorAutores);
    }

    @Override
    public List<Livro> findByAutor(String autor) {
        LinkedList<String> isbns = findManyISBNAutores().get(autor);
        List<Livro> livrosDoAutor = new LinkedList<Livro>();

        Map<String, Livro> livros = findManyLivros();
        for(String isbn: isbns){
            Livro livro = livros.get(isbn);
            livrosDoAutor.add(livro);
        }

        return livrosDoAutor;
    }

    @Override
    public List<Livro> findByTitulo(String titulo) {
        List<Livro> livrosTitulo = new LinkedList<Livro>();
        for(Livro livro: findMany())
            if(livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                livrosTitulo.add(livro);
        return livrosTitulo;
    }

    @Override
    public Livro addLivroEmAutor(Livro obj) {
        String autor = obj.getAutor();
        Map<String, LinkedList<String>> isbnPorAutores = findManyISBNAutores();

        if(isbnPorAutores.get(autor) == null)
            addAutor(autor);

        isbnPorAutores.get(autor).add(obj.getISBN());
        replaceInFile(arquivoAutores, isbnPorAutores);
        return obj;
    }

    @Override
    public void removerLivroDeAutor(Livro obj, String autor) {
        Map<String, LinkedList<String>> isbnPorAutores = findManyISBNAutores();
        LinkedList<String> isbns = isbnPorAutores.get(autor);
        isbns.remove(obj.getISBN());
        isbnPorAutores.put(autor, isbns);

        replaceInFile(arquivoAutores, isbnPorAutores);
    }

    @Override
    public Livro findByISBN(String ISBN) throws LivroException {
        Livro l = findByPrimaryKey(ISBN);
        if (l != null)
            return l;
        throw new LivroException(LivroException.NAO_EXISTENTE);
    }


    private <K,V> Map<K, V> findManyMap(File file){
        Map<K, V> map;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            map = (Map<K, V>) in.readObject();

        } catch (IOException e){
            map = new HashMap<>();
        }
        catch (ClassNotFoundException e) {
            map = new HashMap<>();
        }
        return map;
    }

    private Map<String, Livro> findManyLivros(){
        return findManyMap(arquivo);
    }


    private Map<String, LinkedList<String>> findManyISBNAutores() {
        return findManyMap(arquivoAutores);
    }

    private Map<String, LinkedList<String>> findManyISBNCategorias() {
        return findManyMap(arquivoCategorias);
    }


    private <K, V> boolean replaceInFile(File file, Map<K, V> data){
        deleteMany();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
