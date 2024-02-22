package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.MainApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GerenciaLivrosController implements Initializable {

    @FXML
    private Button btnBusca;

    @FXML
    private MenuButton btnOpcoes;

    @FXML
    private TextField caixaNomeLivro;

    @FXML
    private MenuItem menuItemName;

    @FXML
    private MenuItem menuItemISBN;

    @FXML
    private MenuItem menuItemCategory;

    @FXML
    private MenuItem menuItemAuthor;



    @FXML
    private VBox booksList;

    private ObservableList<Livro> list;

    @FXML
    void actionBtnBusca(ActionEvent event) {
        if (criteria == menuItemAuthor){
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByAutor(caixaNomeLivro.getText())
            );
        }
        else if(criteria == menuItemCategory) {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByCategoria(caixaNomeLivro.getText())
            );
        }
        else if (criteria == menuItemISBN) {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findByPrimaryKey(caixaNomeLivro.getText())
            );
        }
        else {
            list = FXCollections.observableArrayList(
                    DAO.getLivroDAO().findBooksByTitulo(caixaNomeLivro.getText())
            );
        }

        if(list.get(0) == null)
            list = FXCollections.observableArrayList();

        renderList();
    }

    @FXML
    void actionCaixaNomeLivro(ActionEvent event) {

    }

    private MenuItem criteria;

    @FXML
    void byAuthor(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        caixaNomeLivro.setPromptText("Insira o nome do autor");
    }

    @FXML
    void byCategory(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        caixaNomeLivro.setPromptText("Insira a categoria");
    }

    @FXML
    void byISBN(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        caixaNomeLivro.setPromptText("Insira o nome do autor");
    }

    @FXML
    void byName(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        caixaNomeLivro.setPromptText("Insira o nome do livro");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = FXCollections.observableArrayList(DAO.getLivroDAO().findMany());
        renderList();
    }

    public void renderList() {
        booksList.getChildren().clear();
        list.forEach(
                (book) -> renderRow(book)
        );
    }

    private void renderRow(Livro book) {

        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("bib-book-row-view.fxml"));
            Node node = loader.load();
            BibBookRowController bookCtrl = loader.getController();
            bookCtrl.setBookAndInitialize(book);
            booksList.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
