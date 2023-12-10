package com.uefs.librarymanager.dao.reserva;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.utils.FileBehaviour;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReservaDAODisk implements ReservaDAO{
    private File arquivo, arquivoId;
    private static final String NOMEARQUIVO = "reservas";
    private int proximoId;
    public ReservaDAODisk() {
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
        arquivoId = FileBehaviour.gerarArquivo("id" + NOMEARQUIVO);
    }

    @Override
    public Reserva create(Reserva obj) {

        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());

        if (reservasDoLivro == null) {
            reservasDoLivro = new LinkedList<Reserva>();
        }

        obj.setId(proximoID());
        reservasDoLivro.addLast(obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        FileBehaviour.sobreescreverArquivo(arquivo, reservas);

        return obj;

    }
    @Override
    public void delete(Reserva obj) {
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        reservasDoLivro.remove(obj);
        reservas.put(obj.getISBN(),reservasDoLivro);

        FileBehaviour.sobreescreverArquivo(arquivo, reservas);

    }

    @Override
    public void deleteMany() {
        FileBehaviour.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Reserva update(Reserva obj) {

        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        int index = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);

        reservasDoLivro.add(index, obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        FileBehaviour.sobreescreverArquivo(arquivo, reservas);

        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);

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
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);
        if(reservas.get(ISBN) != null)
            if(!reservas.get(ISBN).isEmpty())
                return reservas.get(ISBN).get(0);
        return null;
    }

    @Override
    public int proximoID() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoId))) {
            proximoId = (int) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            proximoId = 0;
        }

        proximoId++;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivoId))) {
            out.writeObject(proximoId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return proximoId;
    }

    @Override
    public Map<String, LinkedList<Reserva>> findManyMap() {
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);
        return reservas;
    }

    @Override
    public boolean filaVazia(String ISBN) throws LivroException {
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);
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

        if (!(leitor.temStatusLivre() &&
                leitor.podeFazerMaisReservas() &&
                DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())))
            return null;

        Reserva reserva = new Reserva(leitor.getId(), livro.getISBN());
        reserva.setId(proximoID());
        leitor.setNumReservas(leitor.getNumReservas()+1);
        DAO.getLeitorDAO().update(leitor);
        create(reserva);

        return reserva;

    }

    @Override
    public void cancelarReserva(Leitor leitor, Livro livro) {

        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);

        LinkedList<Reserva> reservasDoLivro = reservas.get(livro.getISBN());
        for(Reserva r: reservasDoLivro)
            if(r.getIdUsuario().equals(leitor.getId())){
                reservasDoLivro.remove(r);
                reservas.put(r.getISBN(),reservasDoLivro);
                FileBehaviour.sobreescreverArquivo(arquivo, reservas);
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
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);

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

        if (!emprestimoPossivel(leitor, livro))
            return null;

        subtrairLivroDoEstoque(livro);
        delete(reserva);

        return DAO.getEmprestimoDAO().create(
                produzirEmprestimoComPrazo(leitor, livro)
        );
    }

    private boolean emprestimoPossivel(Leitor leitor, Livro livro) throws UsuarioException, LivroException {
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
        Map<String, LinkedList<Reserva>> reservas = FileBehaviour.consultarArquivoMap(arquivo);
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

        FileBehaviour.sobreescreverArquivo(arquivo, reservas);

    }
}