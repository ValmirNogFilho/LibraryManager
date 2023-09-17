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

}
