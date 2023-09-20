package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;
import main.java.com.uefs.librarymanager.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.statusLeitor;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ReservaDAOListTest {

    Livro li;
    Leitor l;
    Reserva r;
    @BeforeEach
    void setUp() {
        li = new Livro("a", "a", "a", "1234", 2000, "a", "a", 10);
        l = new Leitor("a", "a", "7890");
        r = new Reserva(l.getId(), 2, li.getISBN());
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
        assertEquals(id, DAO.getReservaDAO().findByPrimaryKey(li.getISBN()).getIdUsuario());
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
        Reserva r2 = new Reserva("1222", 3, "6767");
        assertNull(DAO.getReservaDAO().findByPrimaryKey(r2.getISBN()));
        assertEquals(l.getId(), DAO.getReservaDAO().findByPrimaryKey(li.getISBN()).getIdUsuario());
    }

    @Test
    void filaVazia() throws LivroException {
        DAO.getReservaDAO().delete(r);
        assertTrue(DAO.getReservaDAO().filaVazia(li.getISBN()));
    }

    @Test
    void failRegistrarReserva() throws LivroException, UsuarioException {
        l.setNumReservas(2);
        try{
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
            DAO.getReservaDAO().registrarReserva(l, li3);
            fail("O usuário conseguiu fazer a reserva mesmo já tendo esse ISBN");
        }catch(Exception e){
            assertEquals(LivroException.LEITOR_TEM_ESSE_ISBN, e.getMessage());
        }
        //a ser continuado

    }

    @Test
    void cancelarReserva() {
    }
}