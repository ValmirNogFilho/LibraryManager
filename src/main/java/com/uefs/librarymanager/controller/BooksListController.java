package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BooksListController{

    private ObservableList<Livro> list;

    @FXML
    private VBox booksList;

    private String urlRow;
    @FXML
    void byAuthor(ActionEvent event) {

    }

    @FXML
    void byCategory(ActionEvent event) {

    }

    @FXML
    void byISBN(ActionEvent event) {

    }

    @FXML
    void byName(ActionEvent event) {

    }

    @FXML
    void searchClick(ActionEvent event) {

    }

    private void renderList() {
        booksList.getChildren().clear();
        list.forEach(
                (book) -> renderRow(book)
        );
    }

    private void renderRow(Livro book) {

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(urlRow));
            Node node = loader.load();
            BookRow bookCtrl = loader.getController();
            bookCtrl.setBook(book);
            booksList.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setListAndInitialize(List<Livro> list) {
        this.list = FXCollections.observableArrayList(list);
        renderList();
    }


    public void setUrlRow(String urlRow) {
        this.urlRow = urlRow;
    }

}
