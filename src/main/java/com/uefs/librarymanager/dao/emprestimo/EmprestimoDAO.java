package main.java.com.uefs.librarymanager.dao.emprestimo;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;

import java.util.List;


public interface EmprestimoDAO extends CRUD<Emprestimo> {
    public List<Emprestimo> findByLeitor(Leitor leitor);
    public int QuantidadeEmAndamentoDoLeitor(Leitor leitor);
}
