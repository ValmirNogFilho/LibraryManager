package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BooksListController{

    private ObservableList<Livro> list;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox booksList;

    @FXML
    private MenuItem menuItemName;

    @FXML
    private MenuItem menuItemISBN;

    @FXML
    private MenuItem menuItemCategory;

    @FXML
    private MenuItem menuItemAuthor;

    private String urlRow;

    private MenuItem criteria;

    @FXML
    void byAuthor(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Insira o nome do autor");
    }

    @FXML
    void byCategory(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Insira a categoria");
    }

    @FXML
    void byISBN(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Insira o nome do autor");
    }

    @FXML
    void byName(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Insira o nome do livro");
    }

    @FXML
    void searchClick(ActionEvent event){
        if (criteria == menuItemAuthor){
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByAutor(searchInput.getText())
            );
        }
        else if(criteria == menuItemCategory) {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByCategoria(searchInput.getText())
            );
        }
        else if (criteria == menuItemISBN) {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findByPrimaryKey(searchInput.getText())
            );
        }
        else {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByTitulo(searchInput.getText())
            );
        }

        renderList();
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
