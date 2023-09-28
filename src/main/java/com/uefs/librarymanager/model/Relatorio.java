package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusEmprestimo;

import java.util.*;

/**
 * Esta classe é responsável por emitir relatórios relacionaados aos livros da biblioteca, os tipos de relatório:
 * Número de livros emprestados;
 * Número de livros reservados;
 * Número de livros em atraso de devolução;
 * Histórico de empréstimos de um leitor específico;
 * Relação dos livros mais populares da biblioteca.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.dao.DAO
 * @see main.java.com.uefs.librarymanager.exceptions.UsuarioException
 * @see utils.statusEmprestimo
 * @see java.util
 */
public class Relatorio {
    /**
     * Este método é responsável por contabilizar todos os livros que estão com o estado de empréstimo em ANDAMENTO.
     * @return quantidade de livros emprestados.
     */
    public static int numLivrosEmprestados(){
        int n = 0;
        for(Emprestimo e: DAO.getEmprestimoDAO().findMany()){
            if(e.getStatus().equals(statusEmprestimo.ANDAMENTO))
                n++;
        }
        return n;
    }

    /**
     * Este método é responsável por contabilizar todos os livros que estão reservados.
     * @return quantidade de livros reservados.
     */
    public static int numLivrosReservados(){
        int n = 0;
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()){
            if(!(reservas.get(ISBN).isEmpty()))
                n++;
        }
        return n;
    }

    /**
     * Este método é responsável por contabilizar todos os livros que estão com estado de empréstimo MULTADO que equivale
     * a atraso de devolução.
     * @return quantidade de livros em atraso de devolução.
     */
    public static int numLivrosAtrasados(){
        int n = 0;
        for(Emprestimo e: DAO.getEmprestimoDAO().findMany()){
            if(e.getStatus().equals(statusEmprestimo.MULTADO))
                n++;
        }
        return n;
    }

    /**
     * Este método dá o parecer dos empréstimos de um usuário específico que é identificado a partir do parâmetro
     * "idUsuario"
     * @param idUsuario
     * @return empréstimos de um usuário específico
     * @throws UsuarioException
     */
    public static List<Emprestimo> historicoEmprestimo(String idUsuario) throws UsuarioException {
        return DAO.getEmprestimoDAO().findByLeitor(DAO.getLeitorDAO().findById(idUsuario));
    }

    /**
     * Este método faz uma ordenação dos livros com base na quantidade de exemplares disponíveis. Quanto menos livros
     * disponíveis houverem de um mesmo exemplar, mais ele está sendo emprestado.
     * @param n
     * @return uma lista com os "n" primeiros livros mais emprestados.
     */
    public static List<Livro> livrosMaisPopulares(int n){
        List<Livro> livros = DAO.getLivroDAO().findMany();

        livros.sort(Comparator.comparingInt(Livro::getDisponiveis));

        return livros.subList(0, Math.min(livros.size(), n));
    }
}
