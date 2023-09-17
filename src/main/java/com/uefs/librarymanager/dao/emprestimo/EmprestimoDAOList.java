package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.reserva.ReservaDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import utils.statusEmprestimo;

import java.time.LocalDate;
import java.util.*;

public class EmprestimoDAOList implements EmprestimoDAO {

    private List<Emprestimo> emprestimos;

    public EmprestimoDAOList(){emprestimos = new ArrayList<Emprestimo>();}

    @Override
    public Emprestimo create(Emprestimo obj) {
        emprestimos.add(obj);
        return obj;
    }

    @Override
    public void delete(Emprestimo obj) {
        emprestimos.remove(obj);
    }

    @Override
    public void deleteMany() {emprestimos = new ArrayList<Emprestimo>();}

    @Override
    public Emprestimo update(Emprestimo obj) {
        int i = emprestimos.indexOf(obj);
        emprestimos.set(i, obj);
        return obj;
    }

    @Override
    public List<Emprestimo> findMany() {
        return emprestimos;
    }

    @Override
    public Emprestimo findByPrimaryKey(String Id) {
        for(Emprestimo e: emprestimos)
            if(e.getId().equals(Id))
                return e;
        return null;
    }

    @Override
    public List<Emprestimo> findByLeitor(Leitor leitor) {
        List<Emprestimo> emprestimosLeitor = new ArrayList<Emprestimo>();
        for(Emprestimo emp: emprestimos){
            if(emp.getUsuarioId().equals(leitor.getId()))
                emprestimosLeitor.add(emp);
        }
        return emprestimosLeitor;
    }

    @Override
    public int quantidadeEmAndamentoDoLeitor(Leitor leitor) {
        int count = 0;
        for(Emprestimo e: findByLeitor(leitor))
            if(e.getStatus().equals(statusEmprestimo.ANDAMENTO))
                count++;
        return count;
    }

    public boolean podeFazerMaisEmprestimos(Leitor leitor) throws UsuarioException {
        if(quantidadeEmAndamentoDoLeitor(leitor) < 3)
            return true;
        else throw new UsuarioException(UsuarioException.LIMITE_EMPRESTIMOS);

    }

    @Override
    public boolean usuarioNaoTemISBN(Leitor leitor, String ISBN) throws LivroException {
        for(Emprestimo e: emprestimos)
            if(e.getUsuarioId().equals(leitor.getId())
            && e.getLivroISBN().equals(ISBN))
                throw new LivroException(LivroException.LEITOR_TEM_ESSE_ISBN);

        return true;
    }

    @Override
    public void registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException{

        LeitorDAO leitorDao = DAO.getLeitorDAO();
        LivroDAO livroDao = DAO.getLivroDAO();
        EmprestimoDAO emprestimoDAO = DAO.getEmprestimoDAO();
        ReservaDAO reservaDAO = DAO.getReservaDAO();


        Leitor leitor = leitorDao.findById(objleitor.getId());
        Livro livro = livroDao.findByISBN(objlivro.getISBN());

        if (leitor.podePegarLivro()
                && emprestimoDAO.podeFazerMaisEmprestimos(leitor)
                && livro.existemDisponiveis()
                && reservaDAO.filaVazia(livro.getISBN())
                && emprestimoDAO.usuarioNaoTemISBN(leitor, livro.getISBN())
        ){
            LocalDate inicio = LocalDate.now();
            LocalDate prazoFim = inicio.plusDays(7);

            Emprestimo emprestimo = new Emprestimo(inicio, prazoFim, leitor.getId(), livro.getISBN());
            emprestimo.setId(emprestimo.proximoID());
            emprestimoDAO.create(emprestimo);
        }

    }

}
