package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.utils.Session;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

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

    private void openLoginPage(ActionEvent event) {
        Session.logoutUser();

        ObservableList<Window> windows = Stage.getWindows();
        while (!windows.isEmpty())
            ((Stage) windows.get(0)).close();

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
