package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import utils.statusEmprestimo;

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
    public int QuantidadeEmAndamentoDoLeitor(Leitor leitor) {
        int count = 0;
        for(Emprestimo e: emprestimos)
            if(e.getUsuarioId().equals(leitor.getId()) &&
            e.getStatus().equals(statusEmprestimo.ANDAMENTO))
                count++;
        return count;
    }
}
