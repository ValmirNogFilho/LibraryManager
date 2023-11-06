package main.java.com.uefs.librarymanager.test.dao.memory;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.model.Leitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //conferindo se leitor criado é igual ao registrado no DAO
        Leitor esperado = DAO.getLeitorDAO().create(new Leitor("lucas", "ali", "4002-8922"));
        esperado.setId(lucas.getId());
        Leitor atual = lucas;
        assertEquals(esperado, atual);
    }

    @Test
    void delete() {
        //conferindo se a lista é subtraída e se o objeto não é encontrado na lista
        DAO.getLeitorDAO().delete(mario);
        int tamanho_esperado = 2;
        Assertions.assertEquals(tamanho_esperado, DAO.getLeitorDAO().findMany().size());
    }

    @Test
    void deleteMany() {
        DAO.getLeitorDAO().deleteMany();
        Assertions.assertEquals(0, DAO.getLeitorDAO().findMany().size());
    }

    @Test
    void update() {
        //conferindo se as alterações de nome e telefone são registradas no DAO
        laiza.setNome("Laiza Gordiano");
        laiza.setTelefone("75 98129-7333");
        Leitor atual = DAO.getLeitorDAO().update(laiza);
        Assertions.assertEquals(laiza, atual);
    }

    @Test
    int findMany() {
        DAO.getLeitorDAO().findMany();
        Assertions.assertEquals(3, DAO.getLeitorDAO().findMany().size());
        return 3;
    }

    @Test
    void findByPrimaryKey() {
        Leitor esperado = new Leitor("fabricio", "ala", "1123-9876");
        esperado.setId(mario.getId()); //mudando chave primária usada no equals() para comparar 2 objetos
        Leitor atual = DAO.getLeitorDAO().findByPrimaryKey(esperado.getId());
        Assertions.assertEquals(esperado, atual);
    }
}


