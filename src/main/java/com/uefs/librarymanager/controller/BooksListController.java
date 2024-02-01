package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksListController implements Initializable {

    private ObservableList<Livro> list;

    @FXML
    private VBox booksList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = FXCollections.observableArrayList(
                DAO.getLivroDAO().findMany()
        );
        renderList();
    }

    private void renderList() {
//        int size = list.size();
        booksList.getChildren().clear();
        list.forEach(
                (book) -> renderRow(book)
        );
//        for (int i = 0; i < size; i++) {
//            book = list.get(i);
//            renderRow(book);
//        }

    }

    private void renderRow(Livro book) {

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("book-row-view.fxml"));
            Node node = loader.load();
            BookRowController bookCtrl = loader.getController();
            bookCtrl.setTitle(book.getTitulo());
            booksList.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
