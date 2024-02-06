package com.uefs.librarymanager.dao;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.cargoUsuario;
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
        kevin = DAO.getOperadorDAO().create(new Usuario("Kevin", "UEFS", "4002-8922", "1234", cargoUsuario.BIBLIOTECARIO));
        valmir = DAO.getOperadorDAO().create(new Usuario("valmir", "rua o", "5698-7854", "6547", cargoUsuario.BIBLIOTECARIO));
        henry = DAO.getOperadorDAO().create(new Usuario("henry", "rua a", "1235-8963","9632", cargoUsuario.BIBLIOTECARIO));
    }



    @Test
    void create() {
        Usuario mario = DAO.getOperadorDAO().create(new Usuario("mario", "a", "1234", "54321", cargoUsuario.BIBLIOTECARIO));
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
        Usuario esperado = new Usuario("mario", "ala", "1123-9876", "4569", cargoUsuario.ADMINISTRADOR);
        esperado.setId(valmir.getId());
        Usuario atual = DAO.getOperadorDAO().findByPrimaryKey(esperado.getId());
        assertEquals(esperado, atual);
    }
}