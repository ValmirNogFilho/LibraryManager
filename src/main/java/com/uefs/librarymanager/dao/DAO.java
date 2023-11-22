package main.java.com.uefs.librarymanager.dao;

import main.java.com.uefs.librarymanager.dao.emprestimo.EmprestimoDAO;
import main.java.com.uefs.librarymanager.dao.emprestimo.EmprestimoDAOList;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAOFile;
import main.java.com.uefs.librarymanager.dao.reserva.ReservaDAO;
import main.java.com.uefs.librarymanager.dao.reserva.ReservaDAOFile;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAOFile;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAO;
import main.java.com.uefs.librarymanager.dao.usuario.operador.OperadorDAOFile;

public class DAO {
    private static LeitorDAO leitorDAO;
    private static OperadorDAO operadorDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static ReservaDAO reservaDAO;

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null)
            leitorDAO = new LeitorDAOFile();
//            leitorDAO = new LeitorDAOList();
        return leitorDAO;
    }

    public static OperadorDAO getOperadorDAO() {
        if(operadorDAO == null)
            //operadorDAO = new OperadorDAOList();
            operadorDAO = new OperadorDAOFile();
        return operadorDAO;
    }

    public static LivroDAO getLivroDAO() {
        if(livroDAO == null)
            //livroDAO = new LivroDAOList();
            livroDAO = new LivroDAOFile();
        return livroDAO;
    }

    public static EmprestimoDAO getEmprestimoDAO() {
        if(emprestimoDAO == null)
            emprestimoDAO = new EmprestimoDAOList();
        return emprestimoDAO;
    }

    public static ReservaDAO getReservaDAO() {
        if(reservaDAO == null)
            //reservaDAO = new ReservaDAOList();
            reservaDAO = new ReservaDAOFile();
        return reservaDAO;
    }


}
