package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ReservaDAO extends CRUD<Reserva>{
    public int proximoID();
    Map<String, LinkedList<Reserva>> findManyMap();
    public boolean filaVazia(String ISBN) throws LivroException;
    public Reserva registrarReserva(Leitor leitor, Livro livro) throws UsuarioException, LivroException;
    public void cancelarReserva(Leitor leitor, Livro livro);
    public Reserva popFila(String ISBN, int index);
    public Reserva findById(int Id);
    public List<Reserva> usuariosAptosParaEmprestimo(String ISBN);
}
