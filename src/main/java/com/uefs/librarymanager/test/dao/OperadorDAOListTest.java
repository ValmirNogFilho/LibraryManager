package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

class OperadorDAOListTest {

    Usuario kevin;
    Usuario valmir;
    Usuario henry;

    @BeforeEach
    void setUp() {
        kevin = DAO.getOperadorDAO().create(new Usuario("Kevin", "UEFS", "4002-8922", "1234"));
        valmir = DAO.getOperadorDAO().create(new Usuario("valmir", "rua o", "5698-7854", "6547"));
        henry = DAO.getOperadorDAO().create(new Usuario("henry", "rua a", "1235-8963","9632"));
    }

    @AfterEach
    void tearDown() {
        DAO.getOperadorDAO().deleteMany();
    }

    @Test
    void create() {
        Usuario esperado = new Usuario("lula","09", "45", "9874");
        esperado.setId(kevin.getId());
        Usuario atual = kevin;
        assertEquals(esperado, atual);
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