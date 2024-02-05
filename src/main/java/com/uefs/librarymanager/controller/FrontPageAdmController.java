package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.model.Administrador;
import com.uefs.librarymanager.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FrontPageAdmController implements Initializable {

    @FXML
    private BorderPane borderPaneAdm;

    @FXML
    private Label admName;

    private Administrador adm;

    @FXML
    void actionInicioAdm(ActionEvent event) {
        openPage("users-list-view.fxml");
    }


    @FXML
    void actionNovoUser(ActionEvent event) {

    }


    @FXML
    void actionRelatorio(ActionEvent event) {
        openPage("relatorio.fxml");
    }


    @FXML
    void actionSairAdm(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adm = (Administrador) Session.getUserInSession();
        openPage("users-list-view.fxml");
        admName.setText("Bem vinda(o)," + adm.getNome());
    }


    private void openPage(String url) {
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource(url));
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
        this.borderPaneAdm.setCenter(root);
    }

}
