package main.java.com.uefs.librarymanager.test.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {
    Leitor l;
    Emprestimo emprestimo;
    Livro li;
    Reserva reserva;
    @BeforeEach
    void setUp() {
        l = DAO.getLeitorDAO().create(new Leitor("Fulano", "", ""));
        emprestimo = DAO.getEmprestimoDAO().create(new Emprestimo(LocalDate.now(), LocalDate.now().plusDays(7), l.getId(), "1234"));
        li = DAO.getLivroDAO().create(new Livro("a", "a",
                "a", "12354", 1999, "a", "a", 10));
        reserva = DAO.getReservaDAO().create(new Reserva(l.getId(), li.getISBN()));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitorDAO().deleteMany();
        DAO.getEmprestimoDAO().deleteMany();
        DAO.getLivroDAO().deleteMany();
        DAO.getReservaDAO().deleteMany();
    }

    @Test
    void atualizarMultas() {
        verificarPossivelMulta();
        Sistema.atualizarMultas();
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));

        assertEquals(l.getStatus(), statusLeitor.MULTADO);
        assertEquals(l.getPrazoMulta(), 3);
        assertEquals(emprestimo.getStatus(), statusEmprestimo.MULTADO);
        assertEquals(emprestimo.getAtraso(), 2);

        Sistema.atualizarMultas();
        Sistema.atualizarMultas();
        emprestimo.setDataFim(LocalDate.now());
        DAO.getEmprestimoDAO().update(emprestimo);
        Sistema.atualizarMultas();

        assertEquals(l.getStatus(), statusLeitor.LIVRE);
        assertEquals(l.getPrazoMulta(), 0);
        assertEquals(emprestimo.getStatus(), statusEmprestimo.CONCLUIDO);


    }

    @Test
    void verificarPossivelMulta() {
        emprestimo.setDataFim(LocalDate.now().minusDays(2));
        DAO.getEmprestimoDAO().update(emprestimo);
        Sistema.verificarPossivelMulta(emprestimo, l);
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));
        assertEquals(l.getStatus(), statusLeitor.MULTADO);
        assertEquals(l.getPrazoMulta(), 4);
        assertEquals(emprestimo.getStatus(), statusEmprestimo.MULTADO);
        assertEquals(emprestimo.getAtraso(), 2);

        emprestimo.setDataFim(LocalDate.now().plusDays(2));
        DAO.getEmprestimoDAO().update(emprestimo);
        boolean estaMultado = Sistema.verificarPossivelMulta(emprestimo, l);
        assertFalse(estaMultado);

    }

    @Test
    void atualizarReservas() {
        li.setDisponiveis(1);
        DAO.getLivroDAO().update(li);
        assertNull(reserva.getDataFim());
        Sistema.atualizarReservas();
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        assertEquals(LocalDate.now().plusDays(3), reserva.getDataFim());

        li.setDisponiveis(0);
        DAO.getLivroDAO().update(li);
        Sistema.atualizarReservas();
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        assertNull(reserva.getDataFim());

        li.setDisponiveis(1);
        DAO.getLivroDAO().update(li);
        reserva.setDataFim(LocalDate.now().minusDays(1));
        DAO.getReservaDAO().update(reserva);
        Sistema.atualizarReservas();
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        assertNull(reserva);

    }

}