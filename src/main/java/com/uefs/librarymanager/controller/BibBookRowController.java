package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BibBookRowController {

    @FXML
    private HBox hBox;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem opcaoAlterar;

    @FXML
    private MenuItem opcaoExibirReservas;

    @FXML
    private MenuItem opcaoRegistroEmprestimo;

    @FXML
    private MenuItem opcaoRemover;

    @FXML
    private Button openBookPage;

    @FXML
    private Label title;

    private Livro book;

    public void setBookAndInitialize(Livro book) {
        this.book = book;
        title.setText(book.getTitulo());
    }

    @FXML
    void actionOpcaoAlterar(ActionEvent event) {
        openNewBookUpdatePage();
    }

    @FXML
    void actionOpcaoExibirReservas(ActionEvent event) {
        List<Usuario> users = new ArrayList<>();

        DAO.getReservaDAO()
                .usuariosAptosParaEmprestimo(book.getISBN())
                .forEach(reserva -> {
                    Usuario usuario = DAO.getLeitorDAO().findByPrimaryKey(reserva.getIdUsuario());
                    if (usuario != null) {
                        users.add(usuario);
                    }
                });

        openNewPage("users-list-view.fxml", users);
    }

    @FXML
    void actionOpcaoRegistarEmprestimo(ActionEvent event) {

    }

    @FXML
    void actionOpcaoRemover(ActionEvent event) {
        if(wantsToExcludeBook()){
            DAO.getLivroDAO().delete(book);
            hBox.setVisible(false);
        }
    }

    private boolean wantsToExcludeBook() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Excluir livro");
        confirmationDialog.setHeaderText("Deseja excluir o livro '" + book.getTitulo() +"'?");
        confirmationDialog.setContentText("Escolha 'OK' para excluir");

        confirmationDialog.showAndWait();

        return confirmationDialog.getResult() == ButtonType.OK;
    }


    @FXML
    void btnClicked(ActionEvent event) {
        Stage currentScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
        contextMenu.show(currentScreen);

    }

    @FXML
    void onHover(MouseEvent event) {
        hBox.setStyle("-fx-background-color: #5356d6;");
    }

    @FXML
    void outHover(MouseEvent event) {
        hBox.setStyle("-fx-background-color: #f4f4f4;");
    }


    private void openNewPage(String url, List<Usuario> list) {

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            Parent root = loader.load();
            UsersListController usersListCtrl = loader.getController();
            usersListCtrl.setUserRowUrl("user-reservation-row-view.fxml");
            usersListCtrl.setListAndRender(list);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openNewBookUpdatePage() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("atualizacao-livros-view.fxml"));
            Parent root = loader.load();
            AtualizacaoLivrosController atualizacaoLivrosCtrl = loader.getController();
            atualizacaoLivrosCtrl.setBookAndRender(book);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
