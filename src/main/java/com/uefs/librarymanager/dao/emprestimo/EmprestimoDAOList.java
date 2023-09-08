package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.model.Emprestimo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmprestimoDAOList implements EmprestimoDAO{
    private Map<String, Emprestimo> emprestimos;

    public EmprestimoDAOList(){
        emprestimos = new HashMap<String, Emprestimo>();
    }


    @Override
    public Emprestimo create(Emprestimo obj) {
        emprestimos.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public void delete(Emprestimo obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Emprestimo update(Emprestimo obj) {
        return null;
    }

    @Override
    public List<Emprestimo> findMany() {
        return null;
    }

    @Override
    public Emprestimo findById(String id) {
        return null;
    }
}
