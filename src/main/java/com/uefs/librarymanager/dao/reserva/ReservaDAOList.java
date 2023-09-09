package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.model.Reserva;

import java.util.*;


public class ReservaDAOList implements ReservaDAO{

    private Map<String, Reserva> reservas;
    public ReservaDAOList(){reservas = new HashMap<String, Reserva>();}

    @Override
    public Reserva create(Reserva obj) {
        reservas.put(obj.getISBN(), obj);
        return obj;
    }

    @Override
    public void delete(Reserva obj) {
        reservas.remove(obj.getISBN());
    }

    @Override
    public void deleteMany() {
        reservas = new HashMap<String, Reserva>();
    }

    @Override
    public Reserva update(Reserva obj) {
        reservas.remove(obj.getISBN());
        reservas.put(obj.getISBN(), obj);
        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        Collection<Reserva> valores = reservas.values();
        return new ArrayList<Reserva>(valores);
    }

    @Override
    public Reserva findByPrimaryKey(String ISBN) {
        return reservas.get(ISBN);
    }
}
