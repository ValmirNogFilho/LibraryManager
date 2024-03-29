package com.uefs.librarymanager.dao.livro;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.utils.FileUtils;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LivroDAODisk implements LivroDAO{

    File arquivo;
    private static final String NOMEARQUIVO= "livros";
  
    public LivroDAODisk(){
        arquivo = FileUtils.obterInstanciaArquivo(NOMEARQUIVO);
    }

    @Override
    public Livro create(Livro obj) {
        Map<String, Livro> livros = findManyMap();
        livros.put(obj.getISBN(), obj);
        FileUtils.sobreescreverArquivo(arquivo, livros);

        return obj;
    }

    @Override
    public void delete(Livro obj) {
        Map<String, Livro> livros = findManyMap();
        livros.remove(obj.getISBN());

        FileUtils.sobreescreverArquivo(arquivo, livros);

    }


    @Override
    public void deleteMany() {
        FileUtils.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Livro update(Livro obj) {
        Map<String, Livro> livros = findManyMap();

        livros.remove(obj.getISBN());
        livros.put(obj.getISBN(), obj);

        FileUtils.sobreescreverArquivo(arquivo, livros);

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
    public List<Livro> findBooksByCategoria(String categoria) {
        return findBooksBy(l -> l.getCategoria().toLowerCase().contains(categoria.toLowerCase()));
    }

    @Override
    public List<Livro> findBooksByAutor(String autor) {
        return findBooksBy(l -> l.getAutor().toLowerCase().contains(autor.toLowerCase()));
    }

    @Override
    public List<Livro> findBooksByTitulo(String titulo) {
        return findBooksBy(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()));
    }

    private List<Livro> findBooksBy(Predicate<Livro> rule){
        return findMany()
                .stream()
                .filter(rule)
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
        return FileUtils.consultarArquivoMap(arquivo);
    }

    @Override
    public List<Livro> findLivrosEmprestadosByLeitor(Leitor leitor){
        return DAO.getEmprestimoDAO().findByLeitor(leitor)
                .stream()
                .map(
                        (emprestimo) -> {
                            return findByPrimaryKey(emprestimo.getLivroISBN());
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findLivrosReservadosByLeitor(Leitor leitor){
        return DAO.getReservaDAO().findByLeitor(leitor)
                .stream()
                .map(
                        (reserva) -> {
                            return findByPrimaryKey(reserva.getISBN());
                        }
                )
                .collect(Collectors.toList());
    }
}
