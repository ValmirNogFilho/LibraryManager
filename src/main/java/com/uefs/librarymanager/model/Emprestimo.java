package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.exceptions.EmprestimoException;
import main.java.com.uefs.librarymanager.utils.IDGenerator;
import main.java.com.uefs.librarymanager.utils.statusEmprestimo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Esta classe é responsável por registrar um empréstimo contendo as informações:
 * Data de início do empréstimo;
 * Data estabelecida para devolução do livro;
 * ID do usuário que está fazendo o empréstimo;
 * ISBN do livro;
 * Com base na data de início e data final do empréstimo, é verificado se há atraso;
 * Status do empréstimo, podendo ser:
 * Em andamento;
 * Concluído;
 * Em atraso (este estado remete ao atraso do leitor em concluir o empréstimo).
 * ID do empréstimo;
 * Número de renovações.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.exceptions.EmprestimoException
 * @see IDGenerator
 * @see statusEmprestimo
 * @see java.time.LocalDate
 * @see java.util.Date
 * @see java.util.Objects
 *
 */
public class Emprestimo {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String usuarioId;
    private String livroISBN;
    private int atraso;
    private statusEmprestimo status;
    private Integer id;
    private int numeroRenovacoes;

    public Emprestimo(LocalDate dataInicio, LocalDate dataFim, String usuarioId, String livroISBN) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuarioId = usuarioId;
        this.livroISBN = livroISBN;
        this.atraso = 0;
        this.status = statusEmprestimo.ANDAMENTO;
        this.id = 0;
        this.numeroRenovacoes = 0;
    }


    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getLivroISBN() {
        return livroISBN;
    }

    public void setLivroISBN(String livroISBN) {
        this.livroISBN = livroISBN;
    }

    public int getAtraso() {
        return atraso;
    }

    public void setAtraso(int atraso) {
        this.atraso = atraso;
    }

    public statusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(statusEmprestimo status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroRenovacoes() {
        return numeroRenovacoes;
    }

    /**
     * Este método verifica se há alguma renovação constando no histórico de determinado empréstimo, caso não tenha havido
     * alguma renovação (numeroRenovacoes == 0), a renovação é efetuada. Caso já tenha ocorido alguma renovação, não é
     * possível renovar o empréstimo novamente.
     * @return True, caso o não haja renovações de dado empréstimo ou uma exceção caso já tenha ocorrido uma renovação.
     * @throws EmprestimoException
     */
    public boolean podeRenovar() throws EmprestimoException {
        if (numeroRenovacoes == 0) return true;
        else throw new EmprestimoException(EmprestimoException.LIMITE_RENOVACOES);
    }


    public void setNumeroRenovacoes(int numeroRenovacoes) {
        this.numeroRenovacoes = numeroRenovacoes;
    }

    /**
     * Este método compara se dois IDs de usuário, dois ISBNs de livro e dois IDs de empréstimo são iguais.
     * @param o
     * @return True, caso não haja qualquer desigualdade entre os objetos comparados, caso contrário é retornado False.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(livroISBN, that.livroISBN) && Objects.equals(id, that.id);
    }
}

