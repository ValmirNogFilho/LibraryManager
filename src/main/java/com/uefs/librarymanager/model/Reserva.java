package com.uefs.librarymanager.model;

import com.uefs.librarymanager.utils.IDGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Esta classe gerencia as reservas da biblioteca, recebendo o ID do usuário e o ISBN do livro que o usuário deseja fazer
 * o empréstimo. Ao ficar apto ao pegar o livro, na fila de reserva, o leitor recebe uma data limite para a retirada do livro
 * representada pela variável "dataFim". Além disso, cada reserva possui seu ID próprio.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see IDGenerator
 * @see java.time.LocalDate
 * @see java.util.Objects
 * @see java.util.PriorityQueue
 * @see java.util.Queue
 */
public class Reserva implements Serializable {
    private String idUsuario;
    private LocalDate dataFim;
    private int id;
    private String ISBN;

    public Reserva(String idUsuario, String isbn) {
        this.idUsuario = idUsuario;
        this.dataFim = null;
        this.ISBN = isbn;
        this.id = 0;
    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Este método faz uma comparação para ver se o ID de duas reservas é o mesmo, ou seja, se se trata de uma única
     * reserva e se o ISBN de dois livros é o mesmo. O método retorna um valor booleano, caso os IDs e os ISBNs forem iguais,
     * é retornado True, caso haja diferença, é retornado False.
     * @param o
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id) && Objects.equals(ISBN, reserva.ISBN);
    }

}
