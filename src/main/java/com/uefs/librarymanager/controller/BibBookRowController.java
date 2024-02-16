package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    }

    @FXML
    void actionOpcaoExibirReservas(ActionEvent event) {

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

}
