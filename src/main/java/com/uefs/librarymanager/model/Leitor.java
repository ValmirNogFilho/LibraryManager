package com.uefs.librarymanager.model;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.utils.statusLeitor;

import java.time.LocalDate;

/**
 * Esta classe é uma extensão da classe "Usuario" contendo as especificidades do tipo de usuério leitor, tais quais:
 * Data início da multa, caso o leitor esteja multado;
 * Prazo da multa, caso o leitor esteja multado;
 * Status do leitor, podendo o leitor ter tês estados:
 * Livre: Leitor apto à fazer empréstimos e/ou reservas;
 * Multado: Leitor penalisado por atraso de devolução de livro, gerando impedimento de fazer empréstimos e/ou reservas
 * por tempo determinado;
 * Bloqueado: Leitor penalisado por algum motivo grave, gerando impedimento de fazer empréstimos e/ou reservas por tempo
 * indeterminado.
 * Número de reservas do leitor.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see UsuarioException
 * @see statusLeitor
 * @see java.time.LocalDate
 * @see java.util.Date
 */
public class Leitor extends Usuario{

    private LocalDate inicioMulta;
    private int prazoMulta;
    private statusLeitor status;
    private int numReservas;

    /**
     * Este método inicializa com leitor em seu "estágio inicial" contendo:
     * Valor nulo em "inicioMulta";
     * Prazo da multa igual a zero;
     * Status inicial como LIVRE;
     * Número de reservas zerado.
     * @param nome
     * @param endereco
     * @param telefone
     */
    public Leitor(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
        this.inicioMulta = null;
        this.prazoMulta = 0;
        this.status = statusLeitor.LIVRE;
        this.numReservas = 0;
    }

    public LocalDate getInicioMulta() {
        return inicioMulta;
    }

    public void setInicioMulta(LocalDate inicioMulta) {
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

    /**
     * Este método é responsável por verificar se o leitor pode ou não pegar livros a partir do seu status no sistema.
     * @return True caso o usuário esteja LIVRE e uma exceção caso o usuário esteja multado ou bloqueado.
     * @throws UsuarioException
     */
    public boolean temStatusLivre() throws UsuarioException {
        return switch (status) {
            case LIVRE -> true;
            case MULTADO -> throw new UsuarioException(UsuarioException.USUARIO_MULTADO);
            case BLOQUEADO -> throw new UsuarioException(UsuarioException.USUARIO_BLOQUEADO);
        };
    }

    public int getNumReservas() {
        return numReservas;
    }

    public void setNumReservas(int numReservas) {
        this.numReservas = numReservas;
    }

    /**
     * Este método verifica a quantidade de reservas na conta do leitor.
     * @return True, caso o usuário possua menos de duas reservas e uma exceção caso o usuário possua duas reservas e
     * esteja tentando fazer mais.
     * @throws UsuarioException
     */
    public boolean podeFazerMaisReservas() throws UsuarioException {
        if (numReservas < 2)
            return true;
            else throw new UsuarioException(UsuarioException.LIMITE_RESERVAS);
    }

    public void setStatus(statusLeitor status) {
        this.status = status;
    }

}
