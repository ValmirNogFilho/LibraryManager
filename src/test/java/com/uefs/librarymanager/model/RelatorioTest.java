package com.uefs.librarymanager.model;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Relatorio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uefs.librarymanager.utils.statusEmprestimo;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelatorioTest {
    Leitor leitor;
    Livro livro;
    Livro livro2;
    @BeforeEach
    void setUp() {
        tearDown();
        leitor = DAO.getLeitorDAO().create(new Leitor("a", "a", "12345678"));
        livro = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1234", 2020,
                "a", "a", 20));
        livro2 = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1235", 2020,
                "a", "a", 20));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
    }

    @Test
    void numLivrosEmprestados() throws LivroException, UsuarioException {
        //verifica se número de livros emprestados é incrementado e decrementado
        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
        assertEquals(1, Relatorio.numLivrosEmprestados());
        DAO.getEmprestimoDAO().devolverLivro(e);
        assertEquals(0, Relatorio.numLivrosEmprestados());
    }

    @Test
    void numLivrosReservados() throws LivroException, UsuarioException {
        //verifica se número de livros reservados é incrementado e decrementado
        DAO.getReservaDAO().registrarReserva(leitor, livro);
        assertEquals(1, Relatorio.numLivrosReservados());
        DAO.getReservaDAO().cancelarReserva(leitor, livro);
        assertEquals(0, Relatorio.numLivrosReservados());
    }

    @Test
    void numLivrosAtrasados() throws LivroException, UsuarioException {
        //verifica se número de livros atrasados (com status MULTADO) é incrementado e decrementado
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

        //criando três objetos de Empréstimo com todos dados necessários e inserindo em EmprestimoDAO,
        //a partir de registrarEmpréstimo()
        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);

        Emprestimo e2 = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro2);

        Emprestimo e3 = DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro3);

        //gerando lista idêntica à que deve ser retornada pelo método testado
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

        emprestimosalterados.add(e);
        emprestimosalterados.addLast(e2);
        emprestimosalterados.addLast(e3);

        //por mais que os empréstimos sofram alterações,
        //a lista de retorno deve ser considerada idêntica a emprestimosalterados
        assertEquals(emprestimosalterados, Relatorio.historicoEmprestimo(leitor.getId()));

    }

    @Test
    void livrosMaisPopulares() throws LivroException, UsuarioException {
        Leitor leitor2 = DAO.getLeitorDAO().create(new Leitor("a", "a", "12345678"));
        DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
        //único livro a ser emprestado até o momento deve ser o mais popular
        assertEquals(livro, Relatorio.livrosMaisPopulares(1).get(0));

        //"livro2", emprestado mais vezes, deve ultrapassar a posição de "livro" no ranking de mais populares
        DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro2);
        DAO.getEmprestimoDAO().registrarEmprestimo(leitor2, livro2);
        assertEquals(livro2, Relatorio.livrosMaisPopulares(2).get(0));

        LinkedList<Livro> livrosPopulares = new LinkedList<Livro>();
        livrosPopulares.add(livro2);
        livrosPopulares.addLast(livro);
        //testando se não ocorre exceção de ultrapassagem dos índices da lista quando é inserido número n
        //superior a quantidade de livros na lista (nesse caso, deve ser retornado a lista completa)
        assertEquals(livrosPopulares, Relatorio.livrosMaisPopulares(2));
        assertEquals(livrosPopulares, Relatorio.livrosMaisPopulares(50));
    }
}