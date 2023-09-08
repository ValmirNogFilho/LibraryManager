package main.java.com.uefs.librarymanager.test.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.SenhaInvalidaException;
import main.java.com.uefs.librarymanager.model.Administrador;
import main.java.com.uefs.librarymanager.model.Leitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {
    public static Administrador a;
    @BeforeEach
    void setUp() {
        a = new Administrador("joao", "ali", "40028922", "é");
    }

    @Test
    void cadastrarLeitor() throws SenhaInvalidaException {


        assertThrows(SenhaInvalidaException.class, () -> a.cadastrarLeitor("a", "b", "123", "1"));
        assertThrows(SenhaInvalidaException.class, () -> a.cadastrarLeitor("a", "b", "123", "a"));
        assertDoesNotThrow(() -> a.cadastrarLeitor("a", "b", "123", "1234"));

        Leitor l = a.cadastrarLeitor("a", "b", "123", "7890");
        Leitor l2 = DAO.getLeitorDAO().findByPrimaryKey(l.getId());
        assertEquals(l, l2);
    }

    @Test
    void cadastrarOperador() {
    }

    @Test
    void bloquearLeitor() {
    }

    @Test
    void removerLeitor() {
    }

    @Test
    void removerOperador() {
    }
}