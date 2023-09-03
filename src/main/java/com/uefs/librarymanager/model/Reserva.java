package main.java.com.uefs.librarymanager.model;

import java.util.PriorityQueue;
import java.util.Queue;

public class Reserva {
    private String livroISBN;
    private Queue<String> fila;

    public String getLivroISBN() {
        return livroISBN;
    }

    public void setLivroISBN(String livroISBN) {
        this.livroISBN = livroISBN;
    }

    public Queue<String> getFila() {
        return fila;
    }

    public void setFila(Queue<String> fila) {
        this.fila = fila;
    }


}
