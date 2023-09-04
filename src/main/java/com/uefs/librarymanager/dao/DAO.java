package main.java.com.uefs.librarymanager.dao;

import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAOList;

public class DAO {
    private static LeitorDAO leitorDAO;

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null)
            leitorDAO = new LeitorDAOList();
        return leitorDAO;
    }



}
