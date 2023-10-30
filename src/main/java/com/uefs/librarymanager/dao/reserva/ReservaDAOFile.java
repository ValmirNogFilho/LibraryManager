package main.java.com.uefs.librarymanager.dao.reserva;

import utils.FileBehaviour;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Emprestimo;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Livro;
import main.java.com.uefs.librarymanager.model.Reserva;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReservaDAOFile implements ReservaDAO{
    private File arquivo;

    public ReservaDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("reservas");
    }

    @Override
    public Reserva create(Reserva obj) {
        return null;
    }

    @Override
    public void delete(Reserva obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Reserva update(Reserva obj) {
        return null;
    }

    @Override
    public List<Reserva> findMany() {
        return null;
    }

    @Override
    public Reserva findByPrimaryKey(String PrimaryKey) {
        return null;
    }

    @Override
    public int proximoID() {
        return 0;
    }

    @Override
    public Map<String, LinkedList<Reserva>> findManyMap() {
        return null;
    }

    @Override
    public boolean filaVazia(String ISBN) throws LivroException {
        return false;
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
