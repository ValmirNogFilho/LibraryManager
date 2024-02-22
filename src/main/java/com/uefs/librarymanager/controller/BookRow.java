package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.MainApplication;
import com.uefs.librarymanager.model.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("book-view.fxml"));
            Node root = loader.load();
            BookController bookCtrl = loader.getController();
            bookCtrl.setBookAndRenderPage(book);

            Stage actualStage = (Stage) Window.getWindows().get(0);
            Scene scene = actualStage.getScene();
            BorderPane mainPane = (BorderPane) scene.lookup("#mainPane");
//            Node main = actualStage.getScene().getRoot();
//            GridPane gridPane = (GridPane) main;
//            BorderPane mainPane = (BorderPane) gridPane.getChildren().get(0);
            mainPane.setCenter(root);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
    }

}
