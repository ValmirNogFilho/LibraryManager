package com.uefs.librarymanager.dao.reserva;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ReservaDAOMemory implements ReservaDAO{

    private Map<String, LinkedList<Reserva>> reservas;
    private int proximoId;
    public ReservaDAOMemory(){
        reservas = new HashMap<String, LinkedList<Reserva>>();
        proximoId = 0;
    }

    @Override
    public int proximoID(){
        return proximoId++;
    }

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
        int index = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);

        reservasDoLivro.add(index, obj);
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
    public Map<String, LinkedList<Reserva>> findManyMap(){
        return reservas;
    }


    @Override
    public Reserva findByPrimaryKey(String ISBN) {
        if(reservas.get(ISBN) != null)
            if(!reservas.get(ISBN).isEmpty())
                return reservas.get(ISBN).get(0);
        return null;
    }

    @Override
    public Reserva findById(int Id) {
        for(Reserva reserva: findMany()){
            if(reserva.getId() == Id)
                return reserva;
        }
        return null;
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
        if (leitor.temStatusLivre() &&
                leitor.podeFazerMaisReservas() &&
                DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN()))
        {
            Reserva reserva = new Reserva(leitor.getId(), livro.getISBN());
            reserva.setId(proximoID());
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
            if(r.getIdUsuario().equals(leitor.getId())){
                delete(r);
                return;
            }

    }
    public List<Reserva> usuariosAptosParaEmprestimo(String ISBN){
        LinkedList<Reserva> reservasDoLivro = reservas.get(ISBN);
        if(!reservasDoLivro.isEmpty()){
            int disponiveis = DAO.getLivroDAO().findByPrimaryKey(ISBN).getDisponiveis();
            return reservasDoLivro.subList(0, Math.min(reservasDoLivro.size(), disponiveis));
        }
        return null;
    }

    @Override
    public Emprestimo registrarEmprestimoPorReserva(Reserva reserva) throws LivroException, UsuarioException {
        Leitor leitor = DAO.getLeitorDAO().findById(reserva.getIdUsuario());
        Livro livro = DAO.getLivroDAO().findByPrimaryKey(reserva.getISBN());

        if (leitor.temStatusLivre()
                && DAO.getEmprestimoDAO().podeFazerMaisEmprestimos(leitor)
                && DAO.getEmprestimoDAO().leitorSemAtrasos(leitor)
                && livro.existemDisponiveis()
                && DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())
        )
        {
            LocalDate inicio = LocalDate.now();
            LocalDate prazoFim = inicio.plusDays(7);

            Emprestimo emprestimo = new Emprestimo(inicio, prazoFim, leitor.getId(), livro.getISBN());
            livro.setDisponiveis(livro.getDisponiveis()-1);
            DAO.getLivroDAO().update(livro);
            DAO.getEmprestimoDAO().create(emprestimo);
            delete(reserva);
            return emprestimo;
        }
        return null;
    }

    @Override
    public void removerReservasDe(Leitor l) {
        for(String ISBN: reservas.keySet())
            for(Reserva reserva: reservas.get(ISBN))
                if(reserva.getIdUsuario().equals(l.getId()))
                    delete(reserva);

    }

}
