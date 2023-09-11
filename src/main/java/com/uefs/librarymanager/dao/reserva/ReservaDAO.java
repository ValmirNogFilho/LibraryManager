package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.CRUD;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.model.Reserva;

public interface ReservaDAO extends CRUD<Reserva>{
    public boolean filaVazia(String ISBN) throws LivroException;
}
