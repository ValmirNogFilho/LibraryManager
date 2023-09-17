package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.emprestimo.EmprestimoDAO;
import main.java.com.uefs.librarymanager.dao.emprestimo.EmprestimoDAOList;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoDAOListTest {
    Emprestimo esperado;
    Leitor l;
    @BeforeEach
    void setUp() {
        l = new Leitor("Fulano", "", "");
        esperado = DAO.getEmprestimoDAO().create(new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "1234"));
    }

    @AfterEach
    void tearDown() {
        DAO.getEmprestimoDAO().deleteMany();
    }

    @Test
    void create() {

        Emprestimo atual = new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "1234");
        atual.setId(esperado.getId());
        assertEquals(atual, esperado);
        assertEquals(atual, DAO.getEmprestimoDAO().findByPrimaryKey("" + atual.getId()));
    }

    @Test
    void delete() {
        DAO.getEmprestimoDAO().delete(esperado);
        assertEquals(0, DAO.getEmprestimoDAO().findMany().size());
        assertNull(DAO.getEmprestimoDAO().findByPrimaryKey("" + esperado.getId()));
    }

    @Test
    void deleteMany() {
        DAO.getEmprestimoDAO().deleteMany();
        assertEquals(0,DAO.getEmprestimoDAO().findMany().size());
    }

    @Test
    void update() {
        assertEquals(esperado, DAO.getEmprestimoDAO().findByPrimaryKey("" + esperado.getId()));
        esperado.setLivroISBN("7890");
        DAO.getEmprestimoDAO().update(esperado);
        assertEquals("7890", DAO.getEmprestimoDAO().findByPrimaryKey("" +  esperado.getId()).getLivroISBN());
    }

    @Test
    void findMany() {
        assertEquals(1, DAO.getEmprestimoDAO().findMany().size());
    }

    @Test
    void findByPrimaryKey() {
        assertEquals(esperado, DAO.getEmprestimoDAO().findByPrimaryKey("" + esperado.getId()));
    }

    @Test
    void findByLeitor() {

        assertEquals(esperado, DAO.getEmprestimoDAO().findByLeitor(l).get(0));
    }

    @Test
    void podeFazerMaisEmprestimos() throws UsuarioException {
        assertTrue(DAO.getEmprestimoDAO().podeFazerMaisEmprestimos(l));
    }

    @Test
    void failPodeFazerMaisEmprestimos() throws UsuarioException {
        DAO.getEmprestimoDAO().create(new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "5678"));
        DAO.getEmprestimoDAO().create(new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "3128"));
        assertEquals(3, DAO.getEmprestimoDAO().findByLeitor(l).size());
        try{
            assertTrue(DAO.getEmprestimoDAO().podeFazerMaisEmprestimos(l));
            fail("Leitor está conseguindo fazer mais empréstimos do que o permitido.");
        }
        catch (Exception e){
            assertEquals(UsuarioException.LIMITE_EMPRESTIMOS, e.getMessage());
        }
    }
    @Test
    void usuarioNaoTemISBN() throws LivroException {
        assertTrue(DAO.getEmprestimoDAO().usuarioNaoTemISBN(l, "1"));
    }

    @Test
    void failUsuarioNaoTemISBN() throws LivroException {
        try {
            assertTrue(DAO.getEmprestimoDAO().usuarioNaoTemISBN(l, "1234"));
            fail("Leitor já possui esse ISBN.");
        }
        catch(Exception e){
            assertEquals(LivroException.LEITOR_TEM_ESSE_ISBN, e.getMessage());
        }
    }
}