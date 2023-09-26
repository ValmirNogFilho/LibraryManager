package main.java.com.uefs.librarymanager.test.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AdministradorTest {
    public static Administrador a;
    @BeforeEach
    void setUp() {
        a = new Administrador("joao", "ali", "40028922");
    }

    @Test
    void cadastrarLeitor() throws UsuarioException {

        try{
            a.cadastrarLeitor("a", "b", "123", "1");
        }catch (Exception e){
            assertEquals(UsuarioException.SENHA_INVALIDA, e.getMessage());
        }

        try{
            a.cadastrarLeitor("a", "b", "123", "a");
        }catch (Exception e){
            assertEquals(UsuarioException.SENHA_INVALIDA, e.getMessage());
        }

        a.cadastrarLeitor("a", "b", "123", "1234");

        Leitor l = a.cadastrarLeitor("a", "b", "123", "7890");
        Leitor l2 = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        assertEquals(l, l2);
    }

    @Test
    void cadastrarOperador() throws UsuarioException {
        Usuario o = a.cadastrarOperador("fulano", "abcd", "12347564", "1234", "Bibliotecário");
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o.getId()), o);
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o.getId()).getClass(), Bibliotecario.class);
        Usuario o2 = a.cadastrarOperador("ciclano", "abcd", "12347564", "1234", "Administrador");
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o2.getId()), o2);
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o2.getId()).getClass(), Administrador.class);
    }

    @Test
    void bloquearLeitor() {
        Leitor l = DAO.getLeitorDAO().create(new Leitor("fulano", "abc", "12345678"));
        a.bloquearLeitor(l);
        assertEquals(DAO.getLeitorDAO().findByPrimaryKey(l.getId()).getStatus(), statusLeitor.BLOQUEADO);
    }

    @Test
    void desbloquearLeitor() throws LivroException, UsuarioException {
        Leitor l = DAO.getLeitorDAO().create(new Leitor("fulano", "abc", "12345678"));
        Livro li = DAO.getLivroDAO().create(new Livro("a", "a", "a", "1234",
                2000, "abc", "a", 15));

        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        DAO.getEmprestimoDAO().devolverLivro(e);
        e.setDataFim(LocalDate.now().minusDays(1));
        DAO.getEmprestimoDAO().create(e);
        Sistema.verificarPossivelMulta(e, l);
        assertEquals(statusEmprestimo.MULTADO, e.getStatus());
        DAO.getEmprestimoDAO().devolverLivro(e);
        a.bloquearLeitor(l);
        a.desbloquearLeitor(l);
        assertEquals(statusLeitor.MULTADO, DAO.getLeitorDAO().findByPrimaryKey(l.getId()).getStatus());

        Leitor l2 = DAO.getLeitorDAO().create(new Leitor("ciclano", "abc", "12345678"));
        a.bloquearLeitor(l2);
        a.desbloquearLeitor(l2);
        assertEquals(statusLeitor.LIVRE, DAO.getLeitorDAO().findByPrimaryKey(l2.getId()).getStatus());
    }

    @Test
    void removerLeitor() {
        Leitor l = DAO.getLeitorDAO().create(new Leitor("fulano", "abc", "12345678"));
        a.removerLeitor(l);
        assertNull(DAO.getLeitorDAO().findByPrimaryKey(l.getId()));
    }

    @Test
    void removerOperador() {
        Usuario o = new Bibliotecario("fulano", "abcd", "78901234");
        o = DAO.getOperadorDAO().create(o);
        a.removerOperador(o);
        assertNull(DAO.getOperadorDAO().findByPrimaryKey(o.getId()));
    }
}