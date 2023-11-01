package main.java.com.uefs.librarymanager.dao.reserva;

import utils.FileBehaviour;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.io.*;
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
        Map<String, LinkedList<Reserva>> reservas = findManyMap();
        LinkedList<Reserva> reservasDoLivro = reservas.get(obj.getISBN());
        int index = reservasDoLivro.indexOf(obj);
        reservasDoLivro.remove(obj);

        reservasDoLivro.add(index, obj);
        reservas.put(obj.getISBN(), reservasDoLivro);

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
        return null;
    }

    @Override
    public void cancelarReserva(Leitor leitor, Livro livro) {

    }

    @Override
    public Reserva findById(int Id) {
        return null;
    }

    @Override
    public List<Reserva> usuariosAptosParaEmprestimo(String ISBN) {
        return null;
    }

    @Override
    public Emprestimo registrarEmprestimoPorReserva(Reserva reserva) throws LivroException, UsuarioException {
        return null;
    }
}
