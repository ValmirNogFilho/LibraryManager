package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FrontPageController implements Initializable {

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
    private Button btnMinhasReservas;

    private Leitor user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = (Leitor) Session.getUserInSession();
        assert btnInicio != null : "fx:id=\"btnInicio\" was not injected: check your FXML file 'Untitled'.";
        assert btnMeusLivros != null : "fx:id=\"btnMeusLivros\" was not injected: check your FXML file 'Untitled'.";
        assert btnPerfil != null : "fx:id=\"btnPerfil\" was not injected: check your FXML file 'Untitled'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'Untitled'.";
        assert painelPrincipal != null : "fx:id=\"painelPrincipal\" was not injected: check your FXML file 'Untitled'.";
        openBooksListWithList("books-list-view.fxml", DAO.getLivroDAO().findMany(), "book-row-view.fxml");
    }

    @FXML
    void inicioAction(ActionEvent event) {
        openBooksListWithList(
                "books-list-view.fxml", DAO.getLivroDAO().findMany(), "book-row-view.fxml"
        );
    }

    @FXML
    void meusLivrosAction(ActionEvent event) {
        openBooksListWithList(
                "books-list-view.fxml", DAO.getLivroDAO().findLivrosEmprestadosByLeitor(user), "book-row-view.fxml");
    }

    @FXML
    void minhasReservasAction(ActionEvent event) {
        openBooksListWithList(
                "books-list-view.fxml", DAO.getLivroDAO().findLivrosReservadosByLeitor(user), "reservation-row-view.fxml");
    }


    @FXML
    void perfilAction(ActionEvent event) {
        openPage("profile.fxml");
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
        this.painelPrincipal.setCenter(root);
    }


    private void openBooksListWithList(String url, List<Livro> list, String urlRow) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            Node root = loader.load();
            BooksListController booksListCtrl = loader.getController();
            booksListCtrl.setUrlRow(urlRow);
            booksListCtrl.setListAndInitialize(list);
            this.painelPrincipal.setCenter(root);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a página: " + e.getMessage());
        }
    }

}


