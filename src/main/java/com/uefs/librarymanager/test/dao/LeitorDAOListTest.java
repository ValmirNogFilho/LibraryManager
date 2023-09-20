package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class LeitorDAOListTest {

    Leitor lucas;
    Leitor mario;
    Leitor laiza;

    @BeforeEach
    void setUp() {
        lucas = DAO.getLeitorDAO().create(new Leitor("lucas", "ali", "4002-8922"));
        mario = DAO.getLeitorDAO().create(new Leitor("mario", "lá", "0000-1111"));
        laiza = DAO.getLeitorDAO().create(new Leitor("laiza", "não sei aonde", "5566-7788"));
    }

    @AfterEach
    void tearDown() {
        DAO.getLeitorDAO().deleteMany();
    }

    @Test
    void create() {
        Leitor esperado = DAO.getLeitorDAO().create(new Leitor("lucas", "ali", "4002-8922"));
        esperado.setId(lucas.getId());
        Leitor atual = lucas;
        assertEquals(esperado, atual);
    }

    @Test
    void delete() {
        DAO.getLeitorDAO().delete(mario);
        int tamanho_esperado = 2;
        Assertions.assertEquals(tamanho_esperado, DAO.getOperadorDAO().findMany().size());
    }

    @Test
    void deleteMany() {
        DAO.getLeitorDAO().deleteMany();
        Assertions.assertEquals(0, DAO.getLeitorDAO().findMany().size());
    }

    @Test
    void update() {
        laiza.setNome("Laiza Gordiano");
        laiza.setTelefone("75 98129-7333");
        Leitor atual = DAO.getLeitorDAO().update(laiza);
        Assertions.assertEquals(laiza, atual);
    }

    @Test
    void findMany() {
        DAO.getLeitorDAO().findMany();
        Assertions.assertEquals(3, DAO.getLeitorDAO().findMany().size());
    }

    @Test
    void findByPrimaryKey() {
        Leitor esperado = new Leitor("fabricio", "ala", "1123-9876");
        esperado.setId(mario.getId());
        Leitor atual = DAO.getLeitorDAO().findByPrimaryKey(esperado.getId());
        Assertions.assertEquals(esperado, atual);
    }
}


