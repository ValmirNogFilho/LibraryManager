package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoDAOListTest {
    Emprestimo esperado;
    Leitor l;
    Livro li;
    @BeforeEach
    void setUp() {
        l = DAO.getLeitorDAO().create(new Leitor("Fulano", "", ""));
        esperado = DAO.getEmprestimoDAO().create(new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "1234"));
        li = DAO.getLivroDAO().create(new Livro("a", "a",
                "a", "12354", 1999, "a", "a", 10));
    }

    @AfterEach
    void tearDown() {
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getLeitorDAO().deleteMany();
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

    @Test
    void failRegistrarEmprestimo() {
        try{
            DAO.getEmprestimoDAO().registrarEmprestimo(new Leitor("a", "b", "12345"), li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(UsuarioException.NAO_EXISTENTE, e.getMessage());}

        try{
            DAO.getEmprestimoDAO().registrarEmprestimo(l, new Livro("a", "a",
                    "a", "12345", 1999, "a", "a", 10));
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.NAO_EXISTENTE, e.getMessage());}

        try{
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.LEITOR_TEM_ESSE_ISBN, e.getMessage());}


        try{
            Reserva r = new Reserva(l.getId(), 2, li.getISBN());
            DAO.getReservaDAO().create(r);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.FILA_NAO_VAZIA, e.getMessage());}

        DAO.getReservaDAO().deleteMany();

        try{
            Livro j = new Livro("a", "a", "a", "1", 2000, "a", "a", 10);
            Livro k = new Livro("a", "a", "a", "2", 2000, "a", "a", 10);
            Livro m = new Livro("a", "a", "a", "3", 2000, "a", "a", 10);
            DAO.getLivroDAO().create(j);
            DAO.getLivroDAO().create(k);
            DAO.getLivroDAO().create(m);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, j);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, k);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, m);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(UsuarioException.LIMITE_EMPRESTIMOS, e.getMessage());}

    }

    @Test
    void registrarEmprestimo() throws LivroException, UsuarioException {
        Emprestimo emprestimo = DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        assertEquals(emprestimo, DAO.getEmprestimoDAO().findByPrimaryKey( String.valueOf(emprestimo.getId())));
    }

    @Test
    void failRenovarEmprestimo() throws LivroException, UsuarioException {
        Emprestimo emp = DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        emp.setNumeroRenovacoes(1);
        DAO.getEmprestimoDAO().update(emp);
        try{
            DAO.getEmprestimoDAO().renovarEmprestimo(l, li);
        } catch(Exception e){
            assertEquals(EmprestimoException.LIMITE_RENOVACOES, e.getMessage());
        }
    }

    @Test
    void renovarEmprestimo() throws LivroException, UsuarioException, EmprestimoException {
        DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        Emprestimo e = DAO.getEmprestimoDAO().renovarEmprestimo(l, li);
        assertEquals(LocalDate.now().plusDays(14), e.getDataFim());
    }

    @Test
    void devolverLivro() throws LivroException, UsuarioException {
        Livro livro = DAO.getLivroDAO().create(new Livro
                ("c", "c",
                "c", "0310", 1999, "a", "a", 10)
        );

        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(l, livro);
        int numeroEmprestimos = DAO.getEmprestimoDAO().quantidadeEmAndamentoDoLeitor(l);


        DAO.getEmprestimoDAO().devolverLivro(e);
        assertEquals(statusEmprestimo.CONCLUIDO, e.getStatus());
        assertEquals(10, livro.getDisponiveis());
        assertEquals(numeroEmprestimos-1, DAO.getEmprestimoDAO().quantidadeEmAndamentoDoLeitor(l));

        e.setDataFim(LocalDate.now().minusDays(1));
        DAO.getEmprestimoDAO().create(e);
        Sistema.verificarPossivelMulta(e, l);
        assertEquals(statusEmprestimo.MULTADO, e.getStatus());
        DAO.getEmprestimoDAO().devolverLivro(e);
        assertEquals(statusEmprestimo.CONCLUIDO, e.getStatus());
        assertEquals(statusLeitor.MULTADO, l.getStatus());
        assertEquals(DAO.getEmprestimoDAO().maiorAtraso(l), e.getAtraso());

    }


}