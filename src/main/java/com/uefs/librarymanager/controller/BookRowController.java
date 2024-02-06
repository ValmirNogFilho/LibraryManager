package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BookRowController {

    @FXML
    private Label title;

    @FXML
    private Button openBookPage;

    private Livro book;

    public void setBook(Livro book) {
        this.book = book;
        this.title.setText(book.getTitulo());
    }



    @FXML
    void btnClicked(ActionEvent event) {
        openBook(book);
    }

    @FXML
    void onHover(MouseEvent event) {

    }

    @FXML
    void outHover(MouseEvent event) {

    }

    private void openBook(Livro book){
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("book-view.fxml"));
            Parent profileView = loader.load();
            BookController bookCtrl = loader.getController();

            bookCtrl.setBookAndRenderPage(book);
            Stage profileStage = new Stage();
            Scene scene = new Scene(profileView);
            profileStage.setResizable(false);
            profileStage.setScene(scene);
            profileStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
