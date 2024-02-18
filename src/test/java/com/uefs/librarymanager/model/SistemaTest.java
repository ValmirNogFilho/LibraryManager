package com.uefs.librarymanager.model;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.uefs.librarymanager.utils.statusEmprestimo;
import com.uefs.librarymanager.utils.statusLeitor;

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
                "a", "12354", 1999, "a", "a", 10, ""));
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
        //simulando último acesso do método a ser testado. Esse valor
        // é usado para calcular a quantidade de dias que deve ser subtraída da multa dos leitores
        LocalDate ultimoAcesso = LocalDate.now().minusDays(1);
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));
        //simulando atraso na entrega do empréstimo e o atualizando no EmprestimoDAO
        emprestimo.setDataFim(LocalDate.now().minusDays(1));
        DAO.getEmprestimoDAO().update(emprestimo);

        //atualização deve tornar empréstimo e leitor multado
        //(sendo o leitor multado com o dobro de dias do atraso do empréstimo)

        Sistema.atualizarMultas(ultimoAcesso);
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        assertEquals(l.getStatus(), statusLeitor.MULTADO);
        assertEquals(2,l.getPrazoMulta());
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));
        assertEquals(emprestimo.getStatus(), statusEmprestimo.MULTADO);
        assertEquals(emprestimo.getAtraso(), 1);


        //executando o método mais duas vezes para simular contabilização de 3 dias
        Sistema.atualizarMultas(ultimoAcesso);
        Sistema.atualizarMultas(ultimoAcesso);
        emprestimo.setDataFim(LocalDate.now());
        DAO.getEmprestimoDAO().update(emprestimo);
        //o método deve cancelar a multa do empréstimo e do leitor, zerando o prazo de multa do último
        Sistema.atualizarMultas(ultimoAcesso);
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        assertEquals(l.getStatus(), statusLeitor.LIVRE);
        assertEquals(l.getPrazoMulta(), 0);
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));
        assertEquals(emprestimo.getStatus(), statusEmprestimo.CONCLUIDO);


    }

    @Test
    void verificarPossivelMulta() {
        //submétodo utilizado dentro de atualizarMultas()
        //simulando atraso em empréstimo e o atualizando em EmpréstimoDAO
        emprestimo.setDataFim(LocalDate.now().minusDays(2));
        DAO.getEmprestimoDAO().update(emprestimo);

        Sistema.verificarPossivelMulta(emprestimo, l);
        l = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        emprestimo = DAO.getEmprestimoDAO().findByPrimaryKey(String.valueOf(emprestimo.getId()));

        //verificação deve tornar empréstimo e leitor multado
        // (sendo o leitor multado com o dobro de dias do atraso do empréstimo)

        assertEquals(l.getStatus(), statusLeitor.MULTADO);
        assertEquals(l.getPrazoMulta(), 4);
        assertEquals(emprestimo.getStatus(), statusEmprestimo.MULTADO);
        assertEquals(emprestimo.getAtraso(), 2);

        //aumentando a data fim do empréstimo para mantê-lo em status de "andamento"
        emprestimo.setDataFim(LocalDate.now().plusDays(2));
        DAO.getEmprestimoDAO().update(emprestimo);
        boolean estaMultado = Sistema.verificarPossivelMulta(emprestimo, l);
        assertFalse(estaMultado);

    }

    @Test
    void atualizarReservas() {
        //deixando a geração de empréstimo permitida apenas ao primeiro aguardador na fila do livro
        li.setDisponiveis(1);
        DAO.getLivroDAO().update(li);

        //até o momento a reserva do primeiro aguardador deve ser null
        assertNull(reserva.getDataFim());

        Sistema.atualizarReservas();
        //a partir daqui, o leitor tem o prazo de 3 dias para buscar o livro, senão será excluído da fila
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        assertEquals(LocalDate.now().plusDays(3), reserva.getDataFim());

        li.setDisponiveis(0); //com nenhum livro disponível, ninguém na fila pode gerar emprestimo dele
        DAO.getLivroDAO().update(li);
        Sistema.atualizarReservas();
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        //a dataFim de prazo do primeiro aguardador da fila para buscar livro deve ser null
        assertNull(reserva.getDataFim());

        li.setDisponiveis(1);
        DAO.getLivroDAO().update(li);
        //simulando ultrapassagem do prazo para primeiro aguardador na fila buscar o livro
        reserva.setDataFim(LocalDate.now().minusDays(1));
        DAO.getReservaDAO().update(reserva);
        Sistema.atualizarReservas();
        //a reserva do primeiro aguardador deve ser removida da fila,
        //sendo o retorno da operação de busca igual a null
        reserva = DAO.getReservaDAO().findById(reserva.getId());
        assertNull(reserva);

    }

}