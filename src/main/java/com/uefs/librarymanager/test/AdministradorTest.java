package main.java.com.uefs.librarymanager.test;

import main.java.com.uefs.librarymanager.exceptions.SenhaInvalidaException;
import main.java.com.uefs.librarymanager.model.Administrador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void cadastrarLeitor() throws SenhaInvalidaException {
        Administrador a = new Administrador("joao", "ali", "40028922", "é");
        assertThrows(SenhaInvalidaException.class, (Executable) a.cadastrarLeitor("a", "b", "123", "1"));
        //assertThrows(SenhaInvalidaException.class, (Executable) a.cadastrarLeitor("a", "b", "123", "1234"));

    }

    @Test
    void cadastrarOperador() throws SenhaInvalidaException {
        Administrador a = new Administrador("joao", "ali", "40028922", "é");
        try {
            assertThrows(SenhaInvalidaException.class, (Executable) a.cadastrarOperador("a", "b", "123", "1", "Administrador"));
        } catch (SenhaInvalidaException e){

        }
        //assertThrows(SenhaInvalidaException.class, (Executable) a.cadastrarLeitor("a", "b", "123", "1234"));
    }
}