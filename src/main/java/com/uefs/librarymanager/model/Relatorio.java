package main.java.com.uefs.librarymanager.model;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {
    public int numLivrosEmprestados(){
        return 0;
    }
    public int numLivrosReservados(){
        return 0;
    }
    public int numLivrosAtrasados(){
        return 0;
    }
    public List<Emprestimo> historicoEmprestimo(String idUsuario){
        return new ArrayList<Emprestimo>();
    }
    public List<Livro> livrosMaisPopulares(){
        return new ArrayList<Livro>();
    }
}
