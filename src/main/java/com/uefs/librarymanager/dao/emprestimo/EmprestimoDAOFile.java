package com.uefs.librarymanager.dao.emprestimo;

import com.uefs.librarymanager.exceptions.EmprestimoException;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.utils.FileBehaviour;

import java.io.File;
import java.util.List;

public class EmprestimoDAOFile implements EmprestimoDAO{

    File arquivo;

    public EmprestimoDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("emprestimos");
    }


    @Override
    public Emprestimo create(Emprestimo obj) {
        return null;
    }

    @Override
    public void delete(Emprestimo obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        return null;
    }

    @Override
    public List<Emprestimo> findMany() {
        return null;
    }

    @Override
    public Emprestimo findByPrimaryKey(String PrimaryKey) {
        return null;
    }

    @Override
    public int getProximoID() {
        return 0;
    }

    @Override
    public List<Emprestimo> findByLeitor(Leitor leitor) {
        return null;
    }

    @Override
    public int qtdEmprestimosEmAndamentoDe(Leitor leitor) {
        return 0;
    }

    @Override
    public boolean podeFazerMaisEmprestimos(Leitor leitor) throws UsuarioException {
        return false;
    }

    @Override
    public boolean usuarioNaoTemISBN(Leitor leitor, String ISBN) throws LivroException {
        return false;
    }

    @Override
    public Emprestimo registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException, LivroException {
        return null;
    }

    @Override
    public Emprestimo renovarEmprestimo(Leitor leitor, Livro livro) throws EmprestimoException, LivroException {
        return null;
    }

    @Override
    public int maiorAtraso(Leitor leitor) {
        return 0;
    }

    @Override
    public Livro devolverLivro(Emprestimo emprestimo) throws LivroException, UsuarioException {
        return null;
    }

    @Override
    public boolean leitorSemAtrasos(Leitor leitor) throws UsuarioException {
        return false;
    }
}
