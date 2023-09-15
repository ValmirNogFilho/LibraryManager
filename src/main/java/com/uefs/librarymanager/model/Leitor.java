package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.LivroException;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusLeitor;

import java.util.Date;
import java.util.Map;

public class Leitor extends Usuario{

    private Date inicioMulta;
    private int prazoMulta;
    private statusLeitor status;
    private int numReservas;

    public Leitor(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
        this.inicioMulta = null;
        this.prazoMulta = 0;
        this.status = statusLeitor.LIVRE;
        this.numReservas = 0;
    }

    public Date getInicioMulta() {
        return inicioMulta;
    }

    public void setInicioMulta(Date inicioMulta) {
        this.inicioMulta = inicioMulta;
    }

    public int getPrazoMulta() {
        return prazoMulta;
    }

    public void setPrazoMulta(int prazoMulta) {
        this.prazoMulta = prazoMulta;
    }

    public statusLeitor getStatus() {
        return status;
    }

    public boolean podePegarLivro () throws UsuarioException {
        switch(status){
            case LIVRE:
                return true;
            case MULTADO:
                throw new UsuarioException("Leitor multado.");
            case BLOQUEADO:
                throw new UsuarioException("Leitor bloqueado.");
        }
        return false;
    }

    public int getNumReservas() {
        return numReservas;
    }

    public void setNumReservas(int numReservas) {
        this.numReservas = numReservas;
    }

    public boolean podeFazerMaisReservas() throws UsuarioException {
        if (numReservas < 2)
            return true;
            else throw new UsuarioException(UsuarioException.LIMITE_RESERVAS);
    }

    public void setStatus(statusLeitor status) {
        this.status = status;
    }

    public void registrarReserva( Livro livro) throws UsuarioException, LivroException {
        if (podePegarLivro() &&
            podeFazerMaisReservas() &&
            DAO.getEmprestimoDAO().usuarioNaoTemISBN(this, livro.getISBN())){
                Reserva reserva = new Reserva(getId(), 3, livro.getISBN());
                DAO.getReservaDAO().create(reserva);
        }
    }


}
