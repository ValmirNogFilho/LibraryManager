package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class OperadorDAOTest {

    Usuario kevin;
    Usuario valmir;
    Usuario henry;

    @BeforeEach
    void setUp() {
        DAO.getOperadorDAO().deleteMany();
        kevin = DAO.getOperadorDAO().create(new Usuario("Kevin", "UEFS", "4002-8922", "1234"));
        valmir = DAO.getOperadorDAO().create(new Usuario("valmir", "rua o", "5698-7854", "6547"));
        henry = DAO.getOperadorDAO().create(new Usuario("henry", "rua a", "1235-8963","9632"));
    }



    @Test
    void create() {
        Usuario mario = DAO.getOperadorDAO().create(new Usuario("mario", "a", "1234", "54321"));
        assertEquals(mario, DAO.getOperadorDAO().findByPrimaryKey(mario.getId()));
        assertEquals(4, DAO.getOperadorDAO().findMany().size());
    }

    @Test
    void delete() {
        DAO.getOperadorDAO().delete(henry);
        int tamanho_esperado = 2;
        assertEquals(tamanho_esperado, DAO.getOperadorDAO().findMany().size());
    }

    @Test
    void deleteMany() {
        DAO.getOperadorDAO().deleteMany();
        assertEquals(0,DAO.getOperadorDAO().findMany().size());
    }

    @Test
    void update() {
        kevin.setNome("Kevin Cordeiro");
        kevin.setTelefone("75 98129-7333");
        Usuario atual = DAO.getOperadorDAO().update(kevin);
        assertEquals(kevin, atual);
    }

    @Test
    void findMany() {
        DAO.getOperadorDAO().findMany();
        assertEquals(3,DAO.getOperadorDAO().findMany().size());
    }

    @Test
    void findByPrimaryKey() {
        Usuario esperado = new Usuario("mario", "ala", "1123-9876", "4569");
        esperado.setId(valmir.getId());
        Usuario atual = DAO.getOperadorDAO().findByPrimaryKey(esperado.getId());
        assertEquals(esperado, atual);
    }
}