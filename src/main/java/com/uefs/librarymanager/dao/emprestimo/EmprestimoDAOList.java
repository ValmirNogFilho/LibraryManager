package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;

import java.util.*;

public class EmprestimoDAOList implements EmprestimoDAO {

    private Map<String, Emprestimo> emprestimos;

    public EmprestimoDAOList(){emprestimos = new HashMap<String, Emprestimo>();}

    @Override
    public Emprestimo create(Emprestimo obj) {
        emprestimos.put(String.valueOf(obj.getId()), obj);
        return obj;
    }

    @Override
    public void delete(Emprestimo obj) {
        emprestimos.remove(obj.getId());

    }

    @Override
    public void deleteMany() {
        emprestimos = new HashMap<String, Emprestimo>();
    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        emprestimos.remove(obj.getId());
        emprestimos.put(String.valueOf(obj.getId()), obj);
        return obj;
    }

    @Override
    public List<Emprestimo> findMany() {
        Collection<Emprestimo> valores = emprestimos.values();
        return new ArrayList<Emprestimo>(valores);
    }

    @Override
    public Emprestimo findByPrimaryKey(String Id) {
        return emprestimos.get(Id);
    }

    @Override
    public List<Emprestimo> findByLeitor(Leitor leitor) {
        List<Emprestimo> emprestimosLeitor = new LinkedList<Emprestimo>();
        for(Emprestimo emp: emprestimos.values()){
            if(emp.getUsuarioId().equals(leitor.getId()))
                emprestimosLeitor.add(emp);
        }
        return emprestimosLeitor;
    }
}
