package com.uefs.librarymanager.model;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.EmprestimoException;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uefs.librarymanager.utils.statusEmprestimo;
import com.uefs.librarymanager.utils.statusLeitor;

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
    void failcadastrarLeitor(){

        try{
            //como a senha inserida não tem o número correto de dígitos (4), uma exceção deve ser lançada
            a.cadastrarLeitor("a", "b", "123", "1");
        }catch (Exception e){
            assertEquals(UsuarioException.SENHA_INVALIDA, e.getMessage());
        }

        try{
            //como a senha inserida não é númerica, uma exceção deve ser lançada
            a.cadastrarLeitor("a", "b", "123", "a");
        }catch (Exception e){
            assertEquals(UsuarioException.SENHA_INVALIDA, e.getMessage());
        }

    }

    @Test
    void cadastrarLeitor() throws UsuarioException {
        //como tudo está correto, o cadastro deve ser realizado em LeitorDAOList
        a.cadastrarLeitor("a", "b", "123", "1234");

        Leitor l = a.cadastrarLeitor("a", "b", "123", "7890");
        Leitor l2 = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        assertEquals(l, l2);

    }



    @Test
    void cadastrarOperador() throws UsuarioException {
        //como tudo está correto, os operadores devem ser registrados em OperadorDAOList
        Usuario o = a.cadastrarOperador("fulano", "abcd", "12347564", "1234", "Bibliotecário");
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o.getId()), o);
        //a classe dessa instância de operador deve ser "Bibliotecario"
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o.getId()).getClass(), Bibliotecario.class);

        Usuario o2 = a.cadastrarOperador("ciclano", "abcd", "12347564", "1234", "Administrador");
        assertEquals(DAO.getOperadorDAO().findByPrimaryKey(o2.getId()), o2);
        //a classe dessa instância de operador deve ser "Administrador"
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
                2000, "abc", "a", 15, ""));

        //criando objeto de Empréstimo com todos dados necessários e inserindo em EmprestimoDAOList,
        //a partir de registrarEmpréstimo()
        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        //devolvendo para finalmente poder fazer as alterações necessárias no objeto e iniciar os testes
        DAO.getEmprestimoDAO().devolverLivro(e);

        //simulando criação de empréstimo com atraso
        e.setDataFim(LocalDate.now().minusDays(1));
        DAO.getEmprestimoDAO().create(e);

        //gerando status de multa para empréstimo de livro (pois está atrasado e não foi devolvido)
        Sistema.verificarPossivelMulta(e, l);
        assertEquals(statusEmprestimo.MULTADO, e.getStatus());

        DAO.getEmprestimoDAO().devolverLivro(e); //aqui o empréstimo está concluido, mas o leitor será multado
        a.bloquearLeitor(l);
        //como o leitor tem uma multa a pagar, ele será multado, por mais que tenha sido desbloqueado
        a.desbloquearLeitor(l);
        assertEquals(statusLeitor.MULTADO, DAO.getLeitorDAO().findByPrimaryKey(l.getId()).getStatus());

        //um leitor com situação regular (sem multas a pagar) deve ter status livre após ser desbloqueado
        Leitor l2 = DAO.getLeitorDAO().create(new Leitor("ciclano", "abc", "12345678"));
        a.bloquearLeitor(l2);
        a.desbloquearLeitor(l2);
        assertEquals(statusLeitor.LIVRE, DAO.getLeitorDAO().findByPrimaryKey(l2.getId()).getStatus());
    }

    @Test
    void removerLeitor() throws EmprestimoException {
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