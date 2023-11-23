package main.java.com.uefs.librarymanager.dao.emprestimo;


import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.*;
import main.java.com.uefs.librarymanager.utils.statusEmprestimo;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmprestimoDAOList implements EmprestimoDAO {

    private List<Emprestimo> emprestimos;
    private Integer proximoID;

    public EmprestimoDAOList(){
        emprestimos = new ArrayList<Emprestimo>();
        proximoID = 0;
    }

    @Override
    public int getProximoID() {
        return proximoID++;
    }

    @Override
    public Emprestimo create(Emprestimo obj) {
        obj.setId(getProximoID());
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
        Integer id = Integer.parseInt(Id);
        for(Emprestimo e: emprestimos)
            if(e.getId() == id)
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
    public int maiorAtraso(Leitor leitor){
       List<Emprestimo> emprestimosDoLeitor = findByLeitor(leitor);
       int atrasoMaior = emprestimosDoLeitor.get(0).getAtraso();

       for(Emprestimo e: emprestimosDoLeitor){
           if(e.getAtraso() > atrasoMaior)
               atrasoMaior = e.getAtraso();
       }

       return atrasoMaior;
    }


    @Override
    public Emprestimo registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException{


        Leitor leitor = DAO.getLeitorDAO().findById(objleitor.getId());
        Livro livro = DAO.getLivroDAO().findByISBN(objlivro.getISBN());
        if (leitor.podePegarLivro()
                && podeFazerMaisEmprestimos(leitor)
                && livro.existemDisponiveis()
                && DAO.getReservaDAO().filaVazia(livro.getISBN())
                && usuarioNaoTemISBN(leitor, livro.getISBN())
        ){
            LocalDate inicio = LocalDate.now();
            LocalDate prazoFim = inicio.plusDays(7);

            Emprestimo emprestimo = new Emprestimo(inicio, prazoFim, leitor.getId(), livro.getISBN());
            livro.setDisponiveis(livro.getDisponiveis()-1);
            DAO.getLivroDAO().update(livro);
            create(emprestimo);
            return emprestimo;
        }
        return null;
    }

    public Emprestimo renovarEmprestimo(Leitor leitor, Livro livro) throws EmprestimoException, LivroException {
        for(Emprestimo e: findByLeitor(leitor)){
            if(e.getLivroISBN().equals(livro.getISBN())){
                if(e.podeRenovar() && DAO.getReservaDAO().filaVazia(livro.getISBN())){
                    e.setDataFim(e.getDataFim().plusDays(7));
                    update(e);
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public Livro devolverLivro(Emprestimo emprestimo) throws LivroException, UsuarioException {

        Livro livro = DAO.getLivroDAO().findByISBN(emprestimo.getLivroISBN());
        livro.setDisponiveis(livro.getDisponiveis()+1);

        Leitor leitor = DAO.getLeitorDAO().findById(emprestimo.getUsuarioId());

        Sistema.verificarPossivelMulta(emprestimo, leitor);
        emprestimo.setStatus(statusEmprestimo.CONCLUIDO);

        DAO.getEmprestimoDAO().update(emprestimo);
        DAO.getLivroDAO().update(livro);
        DAO.getLeitorDAO().update(leitor);

        return livro;
    }
    @Override
    public boolean leitorSemAtrasos(Leitor leitor) throws UsuarioException{
        for(Emprestimo emprestimo: findByLeitor(leitor)){
            if(!emprestimo.getStatus().equals(statusEmprestimo.CONCLUIDO) && LocalDate.now().isAfter(emprestimo.getDataFim()))
                return false;
        }
        return true;
    }
}
