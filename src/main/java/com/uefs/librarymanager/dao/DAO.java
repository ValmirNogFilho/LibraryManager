package com.uefs.librarymanager.dao;

import com.uefs.librarymanager.dao.emprestimo.EmprestimoDAO;
import com.uefs.librarymanager.dao.emprestimo.EmprestimoDAOMemory;
import com.uefs.librarymanager.dao.livro.LivroDAO;
import com.uefs.librarymanager.dao.livro.LivroDAODisk;
import com.uefs.librarymanager.dao.reserva.ReservaDAO;
import com.uefs.librarymanager.dao.reserva.ReservaDAODisk;
import com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import com.uefs.librarymanager.dao.usuario.leitor.LeitorDAODisk;
import com.uefs.librarymanager.dao.usuario.operador.OperadorDAO;
import com.uefs.librarymanager.dao.usuario.operador.OperadorDAODisk;

public class DAO {
    private static LeitorDAO leitorDAO;
    private static OperadorDAO operadorDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static ReservaDAO reservaDAO;

    public static LeitorDAO getLeitorDAO() {
        if (leitorDAO == null)
            leitorDAO = new LeitorDAODisk();
//            leitorDAO = new LeitorDAOList();
        return leitorDAO;
    }

    public static OperadorDAO getOperadorDAO() {
        if(operadorDAO == null)
            //operadorDAO = new OperadorDAOList();
            operadorDAO = new OperadorDAODisk();
        return operadorDAO;
    }

    public static LivroDAO getLivroDAO() {
        if(livroDAO == null)
            //livroDAO = new LivroDAOList();
            livroDAO = new LivroDAODisk();
        return livroDAO;
    }

    public static EmprestimoDAO getEmprestimoDAO() {
        if(emprestimoDAO == null)
            emprestimoDAO = new EmprestimoDAOMemory();
        return emprestimoDAO;
    }

    public static ReservaDAO getReservaDAO() {
        if(reservaDAO == null)
            //reservaDAO = new ReservaDAOList();
            reservaDAO = new ReservaDAODisk();
        return reservaDAO;
    }


}
