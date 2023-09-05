package main.java.com.uefs.librarymanager.dao;

import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAOList;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAOList;
import main.java.com.uefs.librarymanager.model.Usuario;

public class DAO {
    private static LeitorDAO leitorDAO;
    private static OperadorDAO operadorDAO;
    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null)
            leitorDAO = new LeitorDAOList();
        return leitorDAO;
    }

    public static CRUD<Usuario> getOperadorDAO() {
        if(operadorDAO == null)
            operadorDAO = new OperadorDAOList();
        return operadorDAO;
    }
}
