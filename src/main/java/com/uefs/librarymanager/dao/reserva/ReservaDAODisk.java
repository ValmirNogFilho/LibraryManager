package com.uefs.librarymanager.dao.reserva;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.*;
import com.uefs.librarymanager.utils.FileUtils;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservaDAODisk implements ReservaDAO{
    private final File arquivo, arquivoId;
    private static final String NOMEARQUIVO = "reservas";
    private int proximoId;
    public ReservaDAODisk() {
        arquivo = FileUtils.gerarArquivo(NOMEARQUIVO);
        arquivoId = FileUtils.gerarArquivo("id" + NOMEARQUIVO);
    }

    @Override
    public Reserva create(Reserva obj) {

        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());

        if (reservasDoLivro == null) {
            reservasDoLivro = new LinkedList<Reserva>();
        }

        obj.setId(proximoID());
        reservasDoLivro.addLast(obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        FileUtils.sobreescreverArquivo(arquivo, reservas);

        return obj;

    }
    @Override
    public void delete(Reserva obj) {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        reservasDoLivro.remove(obj);
        reservas.put(obj.getISBN(),reservasDoLivro);

        FileUtils.sobreescreverArquivo(arquivo, reservas);

    }

    @Override
    public void deleteMany() {
        FileUtils.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Reserva update(Reserva obj) {

        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        int index = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);

        reservasDoLivro.add(index, obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        FileUtils.sobreescreverArquivo(arquivo, reservas);

        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);

        LinkedList<Reserva> todasReservas = new LinkedList<Reserva>();
        for(String isbn: reservas.keySet()){
            LinkedList<Reserva> res = reservas.get(isbn);
            if(res != null)
                todasReservas.addAll(res);
        }
        return todasReservas;
    }

    @Override
    public Reserva findByPrimaryKey(String ISBN) {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);
        if(reservas.get(ISBN) != null)
            if(!reservas.get(ISBN).isEmpty())
                return reservas.get(ISBN).get(0);
        return null;
    }

    @Override
    public int proximoID() {
        proximoId = FileUtils.consultarArquivoIDs(arquivoId);
        proximoId++;
        FileUtils.sobreescreverArquivo(arquivoId, proximoId);
        return proximoId;
    }

    @Override
    public Map<String, LinkedList<Reserva>> findManyMap() {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);
        return reservas;
    }

    @Override
    public boolean filaVazia(String ISBN) throws LivroException {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);
        LinkedList<Reserva> reservasDoLivro = reservas.get(ISBN);

        if(reservasDoLivro == null || reservasDoLivro.isEmpty())
            return true;

        else throw new LivroException(LivroException.FILA_NAO_VAZIA);

    }

    @Override
    public Reserva registrarReserva(Leitor leitor, Livro livro) throws UsuarioException, LivroException {

        if (!reservaPossivel(leitor, livro))
            return null;

        Reserva reserva = new Reserva(leitor.getId(), livro.getISBN());
        reserva.setId(proximoID());
        leitor.setNumReservas(leitor.getNumReservas()+1);
        DAO.getLeitorDAO().update(leitor);

        return create(reserva);

    }

    private boolean reservaPossivel(Leitor leitor, Livro livro) throws UsuarioException, LivroException {
        return (leitor.temStatusLivre() &&
                leitor.podeFazerMaisReservas() &&
                DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN()));
    }


    @Override
    public void cancelarReserva(Leitor leitor, Livro livro) {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(livro.getISBN());
        for(Reserva r: reservasDoLivro)
            if(r.getIdUsuario().equals(leitor.getId())){
                reservasDoLivro.remove(r);
                reservas.put(r.getISBN(),reservasDoLivro);
                FileUtils.sobreescreverArquivo(arquivo, reservas);

                leitor.setNumReservas(leitor.getNumReservas()-1);
                DAO.getLeitorDAO().update(leitor);
            }

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
    public List<Reserva> usuariosAptosParaEmprestimo(String ISBN) {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(ISBN);
        if(reservasDoLivro == null)
            return new ArrayList<>();

        int disponiveis = DAO.getLivroDAO().findByPrimaryKey(ISBN).getDisponiveis();
        return reservasDoLivro.subList(0, Math.min(reservasDoLivro.size(), disponiveis));

    }

    @Override
    public Emprestimo registrarEmprestimoPorReserva(Reserva reserva) throws LivroException, UsuarioException {
        Leitor leitor = DAO.getLeitorDAO().findById(reserva.getIdUsuario());
        Livro livro = DAO.getLivroDAO().findByPrimaryKey(reserva.getISBN());

        if (!reservaPorEmprestimoPossivel(leitor, livro))
            return null;

        subtrairLivroDoEstoque(livro);
        delete(reserva);

        return DAO.getEmprestimoDAO().create(
                produzirEmprestimoComPrazo(leitor, livro)
        );
    }

    private boolean reservaPorEmprestimoPossivel(Leitor leitor, Livro livro) throws UsuarioException, LivroException {
        return (leitor.temStatusLivre()
                && DAO.getEmprestimoDAO().podeFazerMaisEmprestimos(leitor)
                && DAO.getEmprestimoDAO().leitorSemAtrasos(leitor)
                && livro.existemDisponiveis()
                && DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())
        );
    }

    private Emprestimo produzirEmprestimoComPrazo(Leitor leitor, Livro livro){
        LocalDate inicio = LocalDate.now();
        LocalDate prazoFim = inicio.plusDays(7);

        return new Emprestimo(inicio, prazoFim, leitor.getId(), livro.getISBN());
    }

    private void subtrairLivroDoEstoque(Livro livro){
        livro.setDisponiveis(livro.getDisponiveis()-1);
        DAO.getLivroDAO().update(livro);
    }


    @Override
    public void removerReservasDe(Leitor l) {
        Map<String, LinkedList<Reserva>> reservas = FileUtils.consultarArquivoMap(arquivo);
        LinkedList<Reserva> reservasDoLivro;

        for(String ISBN: reservas.keySet()) {
            reservasDoLivro = reservas.get(ISBN);
            for (Reserva reserva: reservasDoLivro){
                if(reserva.getIdUsuario().equals(l.getId())){
                    reservasDoLivro.remove(reserva);
                    reservas.put(reserva.getISBN(),reservasDoLivro);
                }
            }
        }

        FileUtils.sobreescreverArquivo(arquivo, reservas);
    }

    @Override
    public List<Reserva> findByLeitor(Leitor leitor) {
         return findMany()
                .stream()
                .filter(
                        (reserva) -> reserva.getIdUsuario().equals(leitor.getId())
                )
                .collect(Collectors.toList());

    }

    @Override
    public int getQueuePosition(Usuario user, Livro livro) {
        List<Reserva> queue = findManyMap().get(livro.getISBN());
        if(queue == null)
            return -1;
        for (int index = 0; index < queue.size(); index++) {
            Reserva reserva = queue.get(index);
            if(reserva.getIdUsuario().equals(user.getId()))
                return index;
        }
        return -1;
    }
}
