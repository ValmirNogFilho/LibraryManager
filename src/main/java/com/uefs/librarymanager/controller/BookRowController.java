package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BookRowController implements BookRow{

    @FXML
    private Label title;

    @FXML
    private Button openBookPage;

    private Livro book;

    public void setBook(Livro book) {
        this.book = book;
        this.title.setText(book.getTitulo());
    }

    public void displayButton(boolean value) {
        openBookPage.setVisible(value);
    }

    @Override
    @FXML
    public void btnClicked(ActionEvent event) {
        BookRow.openBook(book);
    }

    @Override
    @FXML
    public void onHover(MouseEvent event) {
    }

    @Override
    @FXML
    public void outHover(MouseEvent event) {

    }

}
