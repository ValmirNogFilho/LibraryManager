package main.java.com.uefs.librarymanager.dao.livro;

import utils.FileBehaviour;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;

import java.io.File;
import java.util.List;

public class LivroDAOFile implements LivroDAO{

    File arquivo;

    public LivroDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("livros");
    }

    @Override
    public Livro create(Livro obj) {
        return null;
    }

    @Override
    public void delete(Livro obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Livro update(Livro obj) {
        return null;
    }

    @Override
    public List<Livro> findMany() {
        return null;
    }

    @Override
    public Livro findByPrimaryKey(String PrimaryKey) {
        return null;
    }

    @Override
    public void addCategoria(String categoria) {

    }

    @Override
    public Livro addLivroEmCategoria(Livro obj) {
        return null;
    }

    @Override
    public void removerLivroDeCategoria(Livro obj, String categoria) {

    }

    @Override
    public List<Livro> findByCategoria(String categoria) {
        return null;
    }

    @Override
    public void addAutor(String autor) {

    }

    @Override
    public List<Livro> findByAutor(String autor) {
        return null;
    }

    @Override
    public List<Livro> findByTitulo(String titulo) {
        return null;
    }

    @Override
    public Livro addLivroEmAutor(Livro obj) {
        return null;
    }

    @Override
    public void removerLivroDeAutor(Livro obj, String autor) {

    }

    @Override
    public Livro findByISBN(String ISBN) throws LivroException {
        return null;
    }
}
