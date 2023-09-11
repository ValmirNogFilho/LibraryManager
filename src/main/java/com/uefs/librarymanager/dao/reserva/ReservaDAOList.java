package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.util.*;


public class ReservaDAOList implements ReservaDAO{

    private Map<String, LinkedList<Reserva>> reservas;
    public ReservaDAOList(){reservas = new HashMap<String, LinkedList<Reserva>>();}

    @Override
    public Reserva create(Reserva obj) {
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        reservasDoLivro.add(obj);
        reservas.put(obj.getISBN(),reservasDoLivro);
        return obj;
    }

    @Override
    public void delete(Reserva obj) {
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        reservasDoLivro.remove(obj);
        reservas.put(obj.getISBN(),reservasDoLivro);
    }

    @Override
    public void deleteMany() {
        reservas = new HashMap<String, LinkedList<Reserva>>();
    }

    @Override
    public Reserva update(Reserva obj) {
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        int i = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);
        reservas.put(obj.getISBN(), reservasDoLivro);
        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        LinkedList<Reserva> todasReservas = new LinkedList<Reserva>();
        for(String isbn: reservas.keySet()){
            LinkedList<Reserva> res = reservas.get(isbn);
            todasReservas.addAll(res);
        }
        return todasReservas;
    }

    @Override
    public Reserva findByPrimaryKey(String ISBN) {
        return reservas.get(ISBN).removeFirst();
    }


    @Override
    public boolean filaVazia(String ISBN) throws LivroException {
        if(reservas.get(ISBN).isEmpty()) return true;
        else throw new LivroException("A fila de reservas desse livro não está vazia");
    }
}
