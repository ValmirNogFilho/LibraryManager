package com.uefs.librarymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
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
        this.openPage("com/uefs/librarymanager/hello-view.fxml");

    }

    @FXML
    void meusLivrosAction(ActionEvent event) {
        this.openPage("books.fxml");
    }

    @FXML
    void perfilAction(ActionEvent event) {

    }

    @FXML
    void sairAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnInicio != null : "fx:id=\"btnInicio\" was not injected: check your FXML file 'Untitled'.";
        assert btnMeusLivros != null : "fx:id=\"btnMeusLivros\" was not injected: check your FXML file 'Untitled'.";
        assert btnPerfil != null : "fx:id=\"btnPerfil\" was not injected: check your FXML file 'Untitled'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'Untitled'.";
        assert painelPrincipal != null : "fx:id=\"painelPrincipal\" was not injected: check your FXML file 'Untitled'.";

    }

    private void openPage(String url) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(url));
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
        this.painelPrincipal.setCenter(root);
    }
}


