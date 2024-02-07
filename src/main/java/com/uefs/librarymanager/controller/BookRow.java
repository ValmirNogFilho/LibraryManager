package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.model.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface BookRow {

    public void setBook(Livro book);
    @FXML
    void btnClicked(ActionEvent event);

    @FXML
    void onHover(MouseEvent event);

    @FXML
    void outHover(MouseEvent event);

    static void openBook(Livro book){

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("book-view.fxml"));
            Node root = loader.load();
            BookController bookCtrl = loader.getController();
            bookCtrl.setBookAndRenderPage(book);

            Node main = HelloController.mainPage.getScene().getRoot();
            GridPane gridPane = (GridPane) main;
            BorderPane mainPane = (BorderPane) gridPane.getChildren().get(0);
            mainPane.setCenter(root);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
    }

}
