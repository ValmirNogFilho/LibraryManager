package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

public interface ReservaDAO extends CRUD<Reserva>{
    public boolean filaVazia(String ISBN) throws LivroException;
    public Reserva registrarReserva(Leitor leitor, Livro livro) throws UsuarioException, LivroException;
    public void cancelarReserva(Leitor leitor, Livro livro);
}
