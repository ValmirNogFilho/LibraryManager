package main.java.com.uefs.librarymanager.dao.livro;

import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;
import utils.FileBehaviour;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class LivroDAOFile implements LivroDAO{

    File arquivo;
   

    private static final String NOMEARQUIVOLIVROS = "livros";
  
    public LivroDAOFile(){
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVOLIVROS);
    
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
    
    @Override
    public List<Livro> findByCategoria(String categoria) {

        return findMany()
                .stream()
                .filter(l -> l.getCategoria().equals(categoria))
                .collect(Collectors.toList());

//        ArrayList<Livro> livros = new ArrayList<>();
//
//        for (String isbn: findManyMap().keySet()) {
//            Livro livro = findByPrimaryKey(isbn);
//
//            if(livro.getCategoria().equals(categoria))
//                livros.add(livro);
//        }
//
//        return livros;

    }

    @Override
    public List<Livro> findByAutor(String autor) {
        return findMany()
                .stream()
                .filter(l -> l.getAutor().equals(autor))
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findByTitulo(String titulo) {

        return findMany()
                .stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
//
//        List<Livro> livrosTitulo = new LinkedList<Livro>();
//        for(Livro livro: findMany())
//            if(livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
//                livrosTitulo.add(livro);
//        return livrosTitulo;
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
