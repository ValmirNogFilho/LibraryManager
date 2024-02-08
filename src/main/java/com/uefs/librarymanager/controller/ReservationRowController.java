package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ReservationRowController implements BookRow{

    @FXML
    private Label title;

    @FXML
    private Button openBookPage;

    private Livro book;

    @FXML
    private Label queue;

    @Override
    public void setBook(Livro book) {
        Usuario user = Session.getUserInSession();
        this.book = book;
        title.setText(book.getTitulo());
        int queuePosition = DAO.getReservaDAO().getQueuePosition(user, book) + 1;
        queue.setText(
                queuePosition > 0 ? "Sua posição na fila: " + queuePosition + "°" : "Não está na fila"
        );
    }

    @Override
    public void btnClicked(ActionEvent event) {
        BookRow.openBook(book);
    }

    @Override
    public void onHover(MouseEvent event) {

    }

    @Override
    public void outHover(MouseEvent event) {

    }

}
