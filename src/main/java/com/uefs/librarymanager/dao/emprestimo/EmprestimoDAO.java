package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.reserva.ReservaDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;

import java.time.LocalDate;
import java.util.List;


public interface EmprestimoDAO extends CRUD<Emprestimo> {
    public int getProximoID();
    public List<Emprestimo> findByLeitor(Leitor leitor);
    public int quantidadeEmAndamentoDoLeitor(Leitor leitor);
    public boolean podeFazerMaisEmprestimos(Leitor leitor) throws UsuarioException;
    public boolean usuarioNaoTemISBN(Leitor leitor, String ISBN) throws LivroException;
    public Emprestimo registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException;
    public void renovarEmprestimo(Leitor leitor, Livro livro) throws UsuarioException, EmprestimoException;
}
