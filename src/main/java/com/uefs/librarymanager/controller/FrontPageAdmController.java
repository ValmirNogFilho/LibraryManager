package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.model.Administrador;
import com.uefs.librarymanager.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        openPage("cadastro-user.fxml");
    }


    @FXML
    void actionRelatorio(ActionEvent event) {
        openPage("relatorio.fxml");
    }


    @FXML
    void actionSairAdm(ActionEvent event) {
        if (!(wantsToLogout()))
            return;

        openLoginPage(event);
    }

    private boolean wantsToLogout() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Sair");
        confirmationDialog.setHeaderText("Deseja sair?");
        confirmationDialog.setContentText("Escolha 'OK' para sair");

        confirmationDialog.showAndWait();

        return confirmationDialog.getResult() == ButtonType.OK;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adm = (Administrador) Session.getUserInSession();
        openPage("users-list-view.fxml");
        admName.setText("Bem vinda(o)," + adm.getNome());
    }

    private void openLoginPage(ActionEvent event) {
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
