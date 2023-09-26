package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusEmprestimo;

import java.util.*;

public class Relatorio {
    public static int numLivrosEmprestados(){
        return DAO.getEmprestimoDAO().findMany().size();
    }
    public static int numLivrosReservados(){
        int n = 0;
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()){
            if(!(reservas.get(ISBN).isEmpty()))
                n++;
        }
        return n;
    }
    public static int numLivrosAtrasados(){
        int n = 0;
        for(Emprestimo e: DAO.getEmprestimoDAO().findMany()){
            if(e.getStatus().equals(statusEmprestimo.MULTADO))
                n++;
        }
        return n;
    }
    public static List<Emprestimo> historicoEmprestimo(String idUsuario) throws UsuarioException {
        return DAO.getEmprestimoDAO().findByLeitor(DAO.getLeitorDAO().findById(idUsuario));
    }
    public static List<Livro> livrosMaisPopulares(int primeiros){
        List<Livro> livros = DAO.getLivroDAO().findMany();

        livros.sort(Comparator.comparingInt(Livro::getDisponiveis));

        return livros.subList(0, Math.min(livros.size(), primeiros));
    }
}
