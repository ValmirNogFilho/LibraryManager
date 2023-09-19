package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.util.*;


public class ReservaDAOList implements ReservaDAO{

    private Map<String, LinkedList<Reserva>> reservas;
    public ReservaDAOList(){reservas = new HashMap<String, LinkedList<Reserva>>();}

    @Override
    public Reserva create(Reserva obj) {
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        if(reservasDoLivro == null){
            reservasDoLivro = new LinkedList<Reserva>();
        }
        reservasDoLivro.addLast(obj);
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
        LinkedList<Reserva> reservasDoLivro = reservas.get(ISBN);
        if(reservasDoLivro != null){
            if(reservasDoLivro.isEmpty()){
                return true;
            }
            else throw new LivroException(LivroException.FILA_NAO_VAZIA);
        }
        return true;
    }

    @Override
    public Reserva registrarReserva(Leitor leitor, Livro livro) throws UsuarioException, LivroException {
        if (leitor.podePegarLivro() &&
                leitor.podeFazerMaisReservas() &&
                DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())){
            Reserva reserva = new Reserva(leitor.getId(), 3, livro.getISBN());
            reserva.setId(reserva.proximoID());
            leitor.setNumReservas(leitor.getNumReservas()+1);
            DAO.getLeitorDAO().update(leitor);
            create(reserva);
            return reserva;
        }
        return null;
    }

    @Override
    public void cancelarReserva(Leitor leitor, Livro livro) {
        LinkedList<Reserva> reservasDoLivro = reservas.get(livro.getISBN());
        for(Reserva r: reservasDoLivro)
            if(r.getIdUsuario().equals(leitor.getId()))
                delete(r);

    }
}
