package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.emprestimo.EmprestimoDAO;
import main.java.com.uefs.librarymanager.dao.livro.LivroDAO;
import main.java.com.uefs.librarymanager.dao.reserva.ReservaDAO;
import main.java.com.uefs.librarymanager.dao.usuario.leitor.LeitorDAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;

import java.time.LocalDate;


public class Bibliotecario extends Usuario {
    public Bibliotecario(String nome, String endereco, String telefone, String senha) {
        super(nome, endereco, telefone, senha);
    }

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
            emprestimoDAO.create(emprestimo);
        }
        // a ser implementado
    }
}
