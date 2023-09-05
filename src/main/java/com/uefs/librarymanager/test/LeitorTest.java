package main.java.com.uefs.librarymanager.test;

import main.java.com.uefs.librarymanager.model.Leitor;
import org.junit.jupiter.api.Test;
import utils.statusLeitor;

import static org.junit.jupiter.api.Assertions.*;

class LeitorTest {

    @Test
    void setStatus() {
        Leitor l = new Leitor("a", "b", "40028922");
        statusLeitor status = statusLeitor.BLOQUEADO;
        l.setStatus(status);

        assertEquals(status, l.getStatus());
    }
}