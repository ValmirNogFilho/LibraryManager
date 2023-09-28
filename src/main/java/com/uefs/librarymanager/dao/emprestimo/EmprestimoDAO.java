package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.*;

import java.util.List;


public interface EmprestimoDAO extends CRUD<Emprestimo> {
    public int getProximoID();
    public List<Emprestimo> findByLeitor(Leitor leitor);
    public int quantidadeEmAndamentoDoLeitor(Leitor leitor);
    public boolean podeFazerMaisEmprestimos(Leitor leitor) throws UsuarioException;
    public boolean usuarioNaoTemISBN(Leitor leitor, String ISBN) throws LivroException;
    public Emprestimo registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException;
    public Emprestimo renovarEmprestimo(Leitor leitor, Livro livro) throws EmprestimoException, LivroException;
    public int maiorAtraso(Leitor leitor);
    public Livro devolverLivro(Emprestimo emprestimo) throws LivroException, UsuarioException;
    public boolean leitorSemAtrasos(Leitor leitor) throws UsuarioException;


}
