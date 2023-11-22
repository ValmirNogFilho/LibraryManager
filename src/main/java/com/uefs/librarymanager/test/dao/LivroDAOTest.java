package main.java.com.uefs.librarymanager.test.dao;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Livro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroDAOTest {
    Livro l, m, n;
    @BeforeEach
    void setUp() {
        l = new Livro("test", "joao", "editora", "1234", 2000, "abc", "Exemplo",10);
        m = new Livro("test", "joao", "editora", "1", 2000, "abc", "Exemplo",10);
        n = new Livro("test", "maria", "editora", "12", 2000, "abc", "Exemplo",10);
        DAO.getLivroDAO().create(l);
        DAO.getLivroDAO().create(m);
        DAO.getLivroDAO().create(n);
    }

    @AfterEach
    void tearDown() {
        DAO.getLivroDAO().deleteMany();
    }

    @Test
    void create() {
        DAO.getLivroDAO().create(new Livro("a", "a", "a", "54321", 2000, "o", "a", 10));
        assertEquals(l, DAO.getLivroDAO().findByPrimaryKey(l.getISBN()));
        assertEquals(4, DAO.getLivroDAO().findMany().size());
    }

    @Test
    void delete() {

        assertEquals(3, DAO.getLivroDAO().findMany().size());
        DAO.getLivroDAO().delete(m);

        assertEquals(2, DAO.getLivroDAO().findMany().size());
        assertNull(DAO.getLivroDAO().findByPrimaryKey(m.getISBN()));
    }

    @Test
    void deleteMany() {
        assertEquals(3, DAO.getLivroDAO().findMany().size());
        DAO.getLivroDAO().deleteMany();
        assertEquals(0, DAO.getLivroDAO().findMany().size());
    }

    @Test
    void update() {
        assertEquals(l, DAO.getLivroDAO().findByPrimaryKey(l.getISBN()));
        l.setAnoDePublicacao(2010);
        DAO.getLivroDAO().update(l);
        assertEquals(2010, DAO.getLivroDAO().findByPrimaryKey(l.getISBN()).getAnoDePublicacao());
    }

    @Test
    void findMany() {
        assertEquals(3, DAO.getLivroDAO().findMany().size());
        //conferindo igualdade de cada um dos livros registrados
        assertEquals(l, DAO.getLivroDAO().findMany().get(2));
        assertEquals(m, DAO.getLivroDAO().findMany().get(0));
        assertEquals(n, DAO.getLivroDAO().findMany().get(1));
    }

    @Test
    void findByISBN() throws LivroException {
        assertEquals(l, DAO.getLivroDAO().findByISBN(l.getISBN()));
    }

    @Test
    void findByTitulo(){
        //conferindo busca de string completa do título ou de substrings contidas
        assertEquals(DAO.getLivroDAO().findMany(), DAO.getLivroDAO().findByTitulo("test"));
        assertEquals(DAO.getLivroDAO().findMany(), DAO.getLivroDAO().findByTitulo("te"));
        //conferindo a geração de lista vazia para títulos não encontrados
        assertTrue(DAO.getLivroDAO().findByTitulo("abc").isEmpty());

        Livro o = DAO.getLivroDAO().create(new Livro("Dom Casmurro", "maria", "editora", "125",
                2000, "abc", "Exemplo",10));
        //conferindo a busca sucessiva por livro a partir de substrings e de buscas com ou sem capitalização
        assertEquals(DAO.getLivroDAO().findByTitulo("Dom").get(0), o);
        assertEquals(DAO.getLivroDAO().findByTitulo("dom").get(0), o);
        assertEquals(DAO.getLivroDAO().findByTitulo("DOM").get(0), o);
    }

    @Test
    void removerLivroDeCategoria() {
        //conferindo exclusão de isbn da lista da categoria a ter um livro subtraído
        int comprimento_inicial = DAO.getLivroDAO().findByCategoria("Exemplo").size();
        DAO.getLivroDAO().removerLivroDeCategoria(l, "Exemplo");
        assertEquals(comprimento_inicial-1, DAO.getLivroDAO().findByCategoria("Exemplo").size());
    }

    @Test
    void findByCategoria() {
        //conferindo busca sucessiva de livros por uma categoria
        DAO.getLivroDAO().create(l);
        DAO.getLivroDAO().create(m);
        assertEquals(l, DAO.getLivroDAO().findByCategoria("Exemplo").get(0));
        assertEquals(m, DAO.getLivroDAO().findByCategoria("Exemplo").get(1));
    }

    @Test
    void findByAutor() {
        //conferindo busca sucessiva de livros por um autor
        DAO.getLivroDAO().create(n);
        assertEquals(n, DAO.getLivroDAO().findByAutor("maria").get(0));
    }

    @Test
    void removerLivroDeAutor() {
        //conferindo remoção de livro de uma lista de autores
        int comprimento_inicial = DAO.getLivroDAO().findByAutor("maria").size();
        DAO.getLivroDAO().removerLivroDeAutor(n, "maria");
        assertEquals(comprimento_inicial-1, DAO.getLivroDAO().findByAutor("maria").size());
    }
}