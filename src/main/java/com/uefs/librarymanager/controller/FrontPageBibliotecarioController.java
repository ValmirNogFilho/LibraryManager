package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.utils.Alerter;
import com.uefs.librarymanager.utils.Session;
import com.uefs.librarymanager.utils.WindowManager;
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

public class FrontPageBibliotecarioController implements Initializable {

    @FXML
    private Label userName;

    @FXML
    private Button btnCadastrarLivros;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnSair;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    void actionCadastrarLivros(ActionEvent event) {
        openPage("cadastro-livros.fxml");
    }

    @FXML
    void inicioAction(ActionEvent event) {
        openPage("gerencia-livros.fxml");
    }

    @FXML
    void livroAction(ActionEvent event) {

    }

    @FXML
    void sairAction(ActionEvent event) {
        if (!(Alerter.wantsToLogoutAlert()))
            return;

        Session.logoutUser();
        WindowManager.closeAllWindows();
        WindowManager.openLoginPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText("Bem vindo, " + Session.getUserInSession().getNome());
        openPage("gerencia-livros.fxml");
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
