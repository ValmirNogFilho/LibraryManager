package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.com.uefs.librarymanager.utils.statusEmprestimo;
import main.java.com.uefs.librarymanager.utils.statusLeitor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoDAOTest {
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
        //conferindo se emprestimo criado é igual ao registrado no DAO
        Emprestimo atual = new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "1234");
        atual.setId(esperado.getId());
        assertEquals(atual, esperado);
        assertEquals(atual, DAO.getEmprestimoDAO().findByPrimaryKey("" + atual.getId()));
    }

    @Test
    void delete() {
        //conferindo se a lista é subtraída e se o objeto não é encontrado na lista
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
        //conferindo se alteração de isbn foi registrada no DAO
        assertEquals(esperado, DAO.getEmprestimoDAO().findByPrimaryKey("" + esperado.getId()));
        esperado.setLivroISBN("7890");
        DAO.getEmprestimoDAO().update(esperado);
        assertEquals("7890", DAO.getEmprestimoDAO().findByPrimaryKey("" +  esperado.getId()).getLivroISBN());
    }

    @Test
    void findMany() {
        //como apenas um empréstimo foi adicionado em setUp, o tamanho deve ser 1
        assertEquals(1, DAO.getEmprestimoDAO().findMany().size());
    }

    @Test
    void findByPrimaryKey() {
        //conferindo se o empréstimo esperado é encontrável pelo id
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
            //Como Leitor l já tem 3 empréstimos, uma exceção deve ser levantada
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
            //como l já possui livro com isbn 1234, uma exceção deve ser levantada
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
            //como o leitor não foi criado em LeitorDAO, uma exceção deve ser levantada
            DAO.getEmprestimoDAO().registrarEmprestimo(new Leitor("a", "b", "12345"), li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(UsuarioException.NAO_EXISTENTE, e.getMessage());}

        try{
            //como o livro não foi criado em LivroDAO, uma exceção deve ser levantada
            DAO.getEmprestimoDAO().registrarEmprestimo(l, new Livro("a", "a",
                    "a", "12345", 1999, "a", "a", 10));
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.NAO_EXISTENTE, e.getMessage());}

        try{
            //como o leitor já fez empréstimo desse isbn, uma exceção deve ser levantada
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.LEITOR_TEM_ESSE_ISBN, e.getMessage());}


        try{
            //como a fila de reservas pra esse livro não está vazia, uma exceção deve ser levantada
            Reserva r = new Reserva(l.getId(), li.getISBN());
            DAO.getReservaDAO().create(r);
            DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
            fail("Exceção não detectada.");
        }catch(Exception e){assertEquals(LivroException.FILA_NAO_VAZIA, e.getMessage());}

        DAO.getReservaDAO().deleteMany();

        try{
            //como o leitor já fez 3 empréstimos, uma exceção deve ser levantada
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
        //como tudo está correto, o empréstimo deve ser registrado no DAO
        assertEquals(emprestimo, DAO.getEmprestimoDAO().findByPrimaryKey( String.valueOf(emprestimo.getId())));
    }

    @Test
    void failRenovarEmprestimo() throws LivroException, UsuarioException {
        Emprestimo emp = DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        emp.setNumeroRenovacoes(1);
        DAO.getEmprestimoDAO().update(emp);
        try{
            //como o leitor já fez 1 renovação do livro, uma exceção deve ser levantada
            DAO.getEmprestimoDAO().renovarEmprestimo(l, li);
        } catch(Exception e){
            assertEquals(EmprestimoException.LIMITE_RENOVACOES, e.getMessage());
        }
    }

    @Test
    void renovarEmprestimo() throws LivroException, UsuarioException, EmprestimoException {
        DAO.getEmprestimoDAO().registrarEmprestimo(l, li);
        Emprestimo e = DAO.getEmprestimoDAO().renovarEmprestimo(l, li);
        //como a data fim equivale a LocalDate.now().plusDays(7), a renovação feita no mesmo dia
        // deve equivaler a LocalDate.now()plusDays(14)
        assertEquals(LocalDate.now().plusDays(14), e.getDataFim());
    }

    @Test
    void devolverLivro() throws LivroException, UsuarioException {
        Livro livro = DAO.getLivroDAO().create(new Livro
                ("c", "c",
                "c", "0310", 1999, "a", "a", 10)
        );

        Emprestimo e = DAO.getEmprestimoDAO().registrarEmprestimo(l, livro);
        //o número de exemplares disponíveis do livro deve ser decrementado em um
        assertEquals(9, livro.getDisponiveis());
        int numeroEmprestimos = DAO.getEmprestimoDAO().quantidadeEmAndamentoDoLeitor(l);


        DAO.getEmprestimoDAO().devolverLivro(e);
        //após devolução de livro, o status do empréstimo deve ser concluído
        assertEquals(statusEmprestimo.CONCLUIDO, e.getStatus());
        //o número de exemplares disponíveis do livro deve ser incrementado a um novamente
        assertEquals(10, livro.getDisponiveis());
        //o número de empréstimos em andamento do leitor deve ser decrementado em um
        assertEquals(numeroEmprestimos-1, DAO.getEmprestimoDAO().quantidadeEmAndamentoDoLeitor(l));

        //alterando empréstimo para simular uma devoulução atrasada
        e.setDataFim(LocalDate.now().minusDays(1));
        DAO.getEmprestimoDAO().create(e);
        Sistema.verificarPossivelMulta(e, l);

        //O sistema deve multar o empréstimo automaticamente
        assertEquals(statusEmprestimo.MULTADO, e.getStatus());
        DAO.getEmprestimoDAO().devolverLivro(e);
        //independentemente da multa, um livro devolvido deve ter empréstimo concluido
        assertEquals(statusEmprestimo.CONCLUIDO, e.getStatus());
        //enquanto o leitor deve ser multado
        assertEquals(statusLeitor.MULTADO, l.getStatus());
        assertEquals(DAO.getEmprestimoDAO().maiorAtraso(l), e.getAtraso());

    }


}