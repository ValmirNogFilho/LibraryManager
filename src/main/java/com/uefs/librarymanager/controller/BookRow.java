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
