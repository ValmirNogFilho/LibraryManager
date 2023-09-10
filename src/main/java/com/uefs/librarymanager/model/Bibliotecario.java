package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;

public class Bibliotecario extends Usuario {
    public Bibliotecario(String nome, String endereco, String telefone, String senha) {
        super(nome, endereco, telefone, senha);
    }

    public void registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException{
        LeitorDAO leitorDao = DAO.getLeitorDAO();
        LivroDAO livroDao = DAO.getLivroDAO();

        Leitor leitor = leitorDao.findById(objleitor.getId());
        Livro livro = livroDao.findByISBN(objlivro.getISBN());
        if (leitor.podePegarLivro());
        // a ser implementado
    }
}
