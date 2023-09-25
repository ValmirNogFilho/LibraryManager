package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusEmprestimo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Relatorio {
    public int numLivrosEmprestados(){
        return DAO.getEmprestimoDAO().findMany().size();
    }
    public int numLivrosReservados(){
        int n = 0;
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()){
            if(!(reservas.get(ISBN).isEmpty()))
                n++;
        }
        return n;
    }
    public int numLivrosAtrasados(){
        int n = 0;
        for(Emprestimo e: DAO.getEmprestimoDAO().findMany()){
            if(e.getStatus().equals(statusEmprestimo.MULTADO))
                n++;
        }
        return n;
    }
    public List<Emprestimo> historicoEmprestimo(String idUsuario) throws UsuarioException {
        return DAO.getEmprestimoDAO().findByLeitor(DAO.getLeitorDAO().findById(idUsuario));
    }
    public List<Livro> livrosMaisPopulares(){
        return new ArrayList<Livro>();
    }
}
