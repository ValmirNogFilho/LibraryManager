package main.java.com.uefs.librarymanager.test.model;

import main.java.com.uefs.librarymanager.exceptions.SenhaInvalidaException;
import main.java.com.uefs.librarymanager.model.Administrador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdministradorTest {

    @Test
    void cadastrarLeitor() throws SenhaInvalidaException {
        Administrador a = new Administrador("joao", "ali", "40028922", "é");

        assertThrows(SenhaInvalidaException.class, () -> a.cadastrarLeitor("a", "b", "123", "1"));
        assertThrows(SenhaInvalidaException.class, () -> a.cadastrarLeitor("a", "b", "123", "a"));
        assertDoesNotThrow(() -> a.cadastrarLeitor("a", "b", "123", "1234"));
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