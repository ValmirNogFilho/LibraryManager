package main.java.com.uefs.librarymanager.dao;

import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAOList;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAOList;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAOList;

public class DAO {
    private static LeitorDAO leitorDAO;
    private static OperadorDAO operadorDAO;
    private static LivroDAO livroDAO;

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null)
            leitorDAO = new LeitorDAOList();
        return leitorDAO;
    }

    public static OperadorDAO getOperadorDAO() {
        if(operadorDAO == null)
            operadorDAO = new OperadorDAOList();
        return operadorDAO;
    }

    public static LivroDAO getLivroDAO() {
        if(livroDAO == null)
            livroDAO = new LivroDAOList();
        return livroDAO;
    }

}
