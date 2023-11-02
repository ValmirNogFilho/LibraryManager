package main.java.com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.DAO;
import utils.FileBehaviour;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReservaDAOFile implements ReservaDAO{
    private File arquivo;
    private FileOutputStream fileOutStream;
    private FileInputStream fileInStream;
    private ObjectInputStream readStream;
    private ObjectOutputStream writeStream;
    private static final String NOMEARQUIVO = "reservas";
    private int proximoId;
    public ReservaDAOFile() {
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    private boolean openStreams(){
        try {
            fileOutStream = new FileOutputStream(NOMEARQUIVO + FileBehaviour.EXTENSAO);
            fileInStream = new FileInputStream(NOMEARQUIVO + FileBehaviour.EXTENSAO);
            writeStream = new ObjectOutputStream(fileOutStream);
            readStream = new ObjectInputStream(fileInStream);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void closeStreams(){
        try {
            writeStream.close();
            readStream.close();
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public Reserva create(Reserva obj) {
        if (!openStreams()){
            return null;
        }

        Map<String, LinkedList<Reserva>> reservas = findManyMap();
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());

        if (reservasDoLivro == null) {
            reservasDoLivro = new LinkedList<Reserva>();
        }

        obj.setId(proximoID());
        reservasDoLivro.addLast(obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        try {
            writeStream.writeObject(reservas);
        } catch (IOException ex) {
            closeStreams();
            return null;
        }
        closeStreams();

        return obj;

    }

    @Override
    public void delete(Reserva obj) {
        Map<String, LinkedList<Reserva>> reservas = findManyMap();
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        reservasDoLivro.remove(obj);
        reservas.put(obj.getISBN(),reservasDoLivro);
        try {
            deleteMany();
            writeStream.writeObject(reservas);
        } catch (IOException ex){}
    }

    @Override
    public void deleteMany() {
        arquivo.delete();
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    @Override
    public Reserva update(Reserva obj) {
        openStreams();
        Map<String, LinkedList<Reserva>> reservas = findManyMap();

        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        int index = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);

        reservasDoLivro.add(index, obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

        try {
            deleteMany();
            writeStream.writeObject(reservas);
        } catch (IOException ex){
            closeStreams();
            return null;
        }
        closeStreams();
        return obj;
    }

    @Override
    public List<Reserva> findMany() {
        Map<String, LinkedList<Reserva>> reservas = findManyMap();
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
        Map<String, LinkedList<Reserva>> reservas = findManyMap();
        if(reservas.get(ISBN) != null)
            if(!reservas.get(ISBN).isEmpty())
                return reservas.get(ISBN).get(0);
        return null;
    }

    @Override
    public int proximoID() {
        return proximoId++;
    }

    @Override
    public Map<String, LinkedList<Reserva>> findManyMap() {
        HashMap<String, LinkedList<Reserva>> reservas;

        try{
            reservas = (HashMap<String, LinkedList<Reserva>>) readStream.readObject();
        }
        catch(Exception arquivoVazio){
            reservas = new HashMap<>();
        }
        return reservas;
    }

    @Override
    public boolean filaVazia(String ISBN) throws LivroException {
        Map<String, LinkedList<Reserva>> reservas = findManyMap();
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
        if (!openStreams()){
            return null;
        }

        if (!(leitor.podePegarLivro() &&
                leitor.podeFazerMaisReservas() &&
                DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())))
            return null;

        Reserva reserva = new Reserva(leitor.getId(), livro.getISBN());
        reserva.setId(proximoID());
        leitor.setNumReservas(leitor.getNumReservas()+1);
        DAO.getLeitorDAO().update(leitor);
        create(reserva);

        Map<String, LinkedList<Reserva>> reservas = findManyMap();

        try {
            deleteMany();
            writeStream.writeObject(reservas);
        } catch (IOException ex){
            closeStreams();
            return null;
        }
        return reserva;

    }

    @Override
    public void cancelarReserva(Leitor leitor, Livro livro) {
        if (!openStreams()){
            return;
        }

        Map<String, LinkedList<Reserva>> reservas = findManyMap();

        LinkedList<Reserva> reservasDoLivro = reservas.get(livro.getISBN());
        for(Reserva r: reservasDoLivro)
            if(r.getIdUsuario().equals(leitor.getId())){
                delete(r);
                try {
                    deleteMany();
                    writeStream.writeObject(reservas);
                } catch (IOException ex){
                    closeStreams();
                    return;
                }
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
        Map<String, LinkedList<Reserva>> reservas = findManyMap();

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

        if (!(leitor.podePegarLivro()
                && DAO.getEmprestimoDAO().podeFazerMaisEmprestimos(leitor)
                && DAO.getEmprestimoDAO().leitorSemAtrasos(leitor)
                && livro.existemDisponiveis()
                && DAO.getEmprestimoDAO().usuarioNaoTemISBN(leitor, livro.getISBN())
        ))
            return null;

        LocalDate inicio = LocalDate.now();
        LocalDate prazoFim = inicio.plusDays(7);

        Emprestimo emprestimo = new Emprestimo(inicio, prazoFim, leitor.getId(), livro.getISBN());
        livro.setDisponiveis(livro.getDisponiveis()-1);
        DAO.getLivroDAO().update(livro);
        DAO.getEmprestimoDAO().create(emprestimo);
        delete(reserva);
        return emprestimo;
    }
}
