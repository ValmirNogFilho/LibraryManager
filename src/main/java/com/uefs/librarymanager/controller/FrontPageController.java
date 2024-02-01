package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FrontPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnMeusLivros;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnSair;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    void inicioAction(ActionEvent event) {
        openPage("books-list-view.fxml");
    }

    @FXML
    void meusLivrosAction(ActionEvent event) {
        openPage("books.fxml");
    }

    @FXML
    void perfilAction(ActionEvent event) {
        openPage("profile.fxml");
    }

    @FXML
    void sairAction(ActionEvent event) {
        Session.logoutUser();
        Stage currentScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentScreen.close();

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            Scene scene = new Scene(root);
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML void initialize() {
        assert btnInicio != null : "fx:id=\"btnInicio\" was not injected: check your FXML file 'Untitled'.";
        assert btnMeusLivros != null : "fx:id=\"btnMeusLivros\" was not injected: check your FXML file 'Untitled'.";
        assert btnPerfil != null : "fx:id=\"btnPerfil\" was not injected: check your FXML file 'Untitled'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'Untitled'.";
        assert painelPrincipal != null : "fx:id=\"painelPrincipal\" was not injected: check your FXML file 'Untitled'.";
        openPage("books-list-view.fxml");
    }

    private void openPage(String url) {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource(url));
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
        this.painelPrincipal.setCenter(root);
    }
}


