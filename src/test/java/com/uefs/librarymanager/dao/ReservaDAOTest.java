package com.uefs.librarymanager.dao;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uefs.librarymanager.utils.statusLeitor;

import static org.junit.jupiter.api.Assertions.*;

class ReservaDAOTest {

    Livro li;
    Leitor l;
    Reserva r;
    @BeforeEach
    void setUp() {
        li = new Livro("a", "a", "a", "1234", 2000, "a", "a", 10);
        l = new Leitor("a", "a", "7890");
        r = new Reserva(l.getId(), li.getISBN());
        DAO.getReservaDAO().create(r);
    }

    @AfterEach
    void tearDown() {
        DAO.getReservaDAO().deleteMany();
    }

    @Test
    void create() {
        Reserva r2 = DAO.getReservaDAO().findByPrimaryKey(li.getISBN());
        assertEquals(li.getISBN(), r2.getISBN());
        assertEquals(l.getId(), r2.getIdUsuario());
    }

    @Test
    void delete() {
        DAO.getReservaDAO().delete(r);
        assertNull(DAO.getReservaDAO().findByPrimaryKey(r.getISBN()));
    }

    @Test
    void deleteMany() {
        DAO.getReservaDAO().deleteMany();
        assertTrue(DAO.getReservaDAO().findMany().isEmpty());
    }

    @Test
    void update() {
        String id = "5555";
        r.setIdUsuario(id);
        DAO.getReservaDAO().update(r);
        assertEquals(id, DAO.getReservaDAO().findById(r.getId()).getIdUsuario());
    }

    @Test
    void findMany() {
        DAO.getReservaDAO().create(r);
        DAO.getReservaDAO().create(r);
        assertEquals(3, DAO.getReservaDAO().findMany().size());
        for(int i = 0; i< 3; i++)
            assertEquals(r.getIdUsuario(), DAO.getReservaDAO().findByPrimaryKey(li.getISBN()).getIdUsuario());
    }

    @Test
    void findByPrimaryKey() {
        Reserva r2 = new Reserva("1222", "6767");
        assertNull(DAO.getReservaDAO().findByPrimaryKey(r2.getISBN()));
        assertEquals(l.getId(), DAO.getReservaDAO().findByPrimaryKey(li.getISBN()).getIdUsuario());
    }

    @Test
    void filaVazia() throws LivroException {
        //verifica retorno lógico "verdadeiro" para quando um livro não houver aguardadores na fila de reservas
        DAO.getReservaDAO().delete(r);
        assertTrue(DAO.getReservaDAO().filaVazia(li.getISBN()));
    }

    @Test
    void failRegistrarReserva() throws LivroException, UsuarioException {
        l.setNumReservas(2);
        try{
            //como o usuário já tem 2 reservas, uma exceção deve ser lançada
            DAO.getReservaDAO().registrarReserva(l, li);
            fail("O usuário conseguiu fazer a reserva");
        }catch(Exception e){
            assertEquals(UsuarioException.LIMITE_RESERVAS, e.getMessage());
        }
        l.setNumReservas(0);
        try{
            Livro li2 = new Livro("a", "a", "a", "123", 2000, "a", "a", 10);
            Livro li3 = new Livro("b", "b", "b", "123", 2001, "c", "d", 11);
            DAO.getLeitorDAO().create(l);
            DAO.getLivroDAO().create(li2);
            DAO.getLivroDAO().create(li3);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li2);
            //como o usuário já fez empréstimo de um livro com esse ISBN, uma exceção deve ser lançada
            DAO.getReservaDAO().registrarReserva(l, li3);
            fail("O usuário conseguiu fazer a reserva mesmo já tendo esse ISBN");
        }catch(Exception e){
            assertEquals(LivroException.LEITOR_TEM_ESSE_ISBN, e.getMessage());
        }
        DAO.getLeitorDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();

        try{
            l.setStatus(statusLeitor.BLOQUEADO);
            //como o usuário bloqueado tentou fazer uma reserva, uma exceção deve ser lançada
            DAO.getReservaDAO().registrarReserva(l, li);
            fail("O usuário conseguiu fazer a reserva estando bloqueado");
        }catch(Exception e){
            assertEquals(UsuarioException.USUARIO_BLOQUEADO, e.getMessage());
        }

        try{
            l.setStatus(statusLeitor.MULTADO);
            //como o usuário multado tentou fazer uma reserva, uma exceção deve ser lançada
            DAO.getReservaDAO().registrarReserva(l, li);
            fail("O usuário conseguiu fazer a reserva estando multado");
        }catch(Exception e){
            assertEquals(UsuarioException.USUARIO_MULTADO, e.getMessage());
        }

    }

    @Test
    void registrarReserva() throws LivroException, UsuarioException {
        Livro li2 = DAO.getLivroDAO().create(new Livro("a", "a", "a", "123", 2000, "a", "a", 10));
        int tamanho_inicial = DAO.getReservaDAO().findMany().size();

        //como tudo está correto, a reserva deve ser criada com os dados recebidos, e a quantidade total de reservas
        // deve ser incrementada em 1
        DAO.getReservaDAO().registrarReserva(l, li2);
        assertEquals(tamanho_inicial+1, DAO.getReservaDAO().findMany().size());
        assertEquals(l.getId(), r.getIdUsuario());
        assertEquals(li.getISBN(), r.getISBN());
    }

    @Test
    void cancelarReserva() {
        //verifica se busca por reserva cancelada tem retorno nulo
        DAO.getReservaDAO().cancelarReserva(l, li);
        assertNull(DAO.getReservaDAO().findByPrimaryKey(li.getISBN()));
    }

    @Test
    void registrarEmprestimoPorReserva() throws LivroException, UsuarioException {
        //não foi feito um failregistrarEmprestimoPorReserva(), pois todos os testes para o funcionamento
        //já foram realizados em failregistrarEmprestimo(), na classe EmprestimoDAOTest
        DAO.getLivroDAO().create(li);
        DAO.getLeitorDAO().create(l);
        //como tudo está correto, o usuário na fila de reserva deve conseguir registrar
        //empréstimo e sua reserva deve ser excluída dda fila
        Emprestimo e = DAO.getReservaDAO().registrarEmprestimoPorReserva(r);
        assertTrue(DAO.getReservaDAO().filaVazia(li.getISBN()));
        assertEquals(e, DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(e.getId())));
    }

    @Test
    void removerReservasDe(){
        assertEquals(r, DAO.getReservaDAO().findById(r.getId()));
        DAO.getReservaDAO().removerReservasDe(l);
        assertNull(DAO.getReservaDAO().findById(r.getId()));
    }

}