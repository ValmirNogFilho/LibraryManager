package main.java.com.uefs.librarymanager.test.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Relatorio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.statusEmprestimo;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelatorioTest {
    Leitor leitor;
    Livro livro;
    Livro livro2;
    @BeforeEach
    void setUp() {
        leitor = DAO.getLeitorDAO().create(new Leitor("a", "a", "12345678"));
        livro = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1234", 2020,
                "a", "a", 20));
        Livro livro2 = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1235", 2020,
                "a", "a", 20));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
    }

    @Test
    void numLivrosEmprestados() throws LivroException, UsuarioException {
        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
        assertEquals(1, Relatorio.numLivrosEmprestados());
        DAO.getEmprestimoDAO().devolverLivro(e);
        assertEquals(0, Relatorio.numLivrosEmprestados());
    }

    @Test
    void numLivrosReservados() throws LivroException, UsuarioException {
        DAO.getReservaDAO().registrarReserva(leitor, livro);
        assertEquals(1, Relatorio.numLivrosReservados());
        DAO.getReservaDAO().cancelarReserva(leitor, livro);
        assertEquals(0, Relatorio.numLivrosReservados());
    }

    @Test
    void numLivrosAtrasados() throws LivroException, UsuarioException {
        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
        e.setStatus(statusEmprestimo.MULTADO);
        DAO.getEmprestimoDAO().update(e);
        assertEquals(1, Relatorio.numLivrosAtrasados());

        e.setStatus(statusEmprestimo.CONCLUIDO);
        DAO.getEmprestimoDAO().update(e);
        assertEquals(0, Relatorio.numLivrosAtrasados());
    }

    @Test
    void historicoEmprestimo() throws LivroException, UsuarioException {
        Livro livro3 = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1236", 2020,
                "a", "a", 20));

        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
        Emprestimo e2 = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro2);
        Emprestimo e3 = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro3);

        LinkedList<Emprestimo> emprestimos = new LinkedList<Emprestimo>();
        emprestimos.add(e);
        emprestimos.addLast(e2);
        emprestimos.addLast(e3);

        assertEquals(emprestimos, Relatorio.historicoEmprestimo(leitor.getId()));

        LinkedList<Emprestimo> emprestimosalterados = new LinkedList<Emprestimo>();

        e2.setStatus(statusEmprestimo.MULTADO);
        e3.setStatus(statusEmprestimo.CONCLUIDO);
        DAO.getEmprestimoDAO().update(e2);
        DAO.getEmprestimoDAO().update(e3);

        emprestimos.add(e);
        emprestimos.addLast(e2);
        emprestimos.addLast(e3);

        assertEquals(emprestimos, Relatorio.historicoEmprestimo(leitor.getId()));

    }

    @Test
    void livrosMaisPopulares() throws LivroException, UsuarioException {
        Leitor leitor2 = DAO.getLeitorDAO().create(new Leitor("a", "a", "12345678"));
        DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);

        assertEquals(livro, Relatorio.livrosMaisPopulares(1).get(0));

        DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro2);
        DAO.getEmprestimoDAO().registrarEmprestimo(leitor2, livro2);

        LinkedList<Livro> livrosPopulares = new LinkedList<Livro>();
        livrosPopulares.add(livro2);
        livrosPopulares.addLast(livro);

        assertEquals(livro2, Relatorio.livrosMaisPopulares(2).get(0));
        assertEquals(livrosPopulares, Relatorio.livrosMaisPopulares(2));
    }
}