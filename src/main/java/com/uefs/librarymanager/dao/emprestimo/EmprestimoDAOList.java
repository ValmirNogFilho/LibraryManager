package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.model.Emprestimo;

import java.util.*;

public class EmprestimoDAOList implements EmprestimoDAO{
    Map<String, LinkedList<Emprestimo>> emprestimos = new HashMap<String, LinkedList<Emprestimo>>();
    @Override
    public Emprestimo create(Emprestimo obj) {
        return null;
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
    public Emprestimo findByPrimaryKey(String id) {
        return null;
    }
}
