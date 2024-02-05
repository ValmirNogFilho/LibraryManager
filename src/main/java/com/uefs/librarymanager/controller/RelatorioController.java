package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    @FXML
    private Button btnBuscar;

    @FXML
    private MenuButton btnTopLivros;

    @FXML
    private TextArea qntdLivrosReservados;

    @FXML
    private TextArea qntLivrosEmprestados;

    @FXML
    private TextArea qntdLivrosAtraso;

    @FXML
    private MenuItem topCinco;

    @FXML
    private MenuItem topDois;

    @FXML
    private MenuItem topQuatro;

    @FXML
    private MenuItem topTres;

    @FXML
    private MenuItem topUm;

    @FXML
    private VBox booksList;

    private int numLivros;


    @FXML
    void acessarLivro(ActionEvent event) {

    }

    @FXML
    void actionBuscar(ActionEvent event) {
        feedBooksList();
    }

    @FXML
    void actionTopCinco(ActionEvent event) {
        numLivros = 5;
    }

    @FXML
    void actionTopQuatro(ActionEvent event) {
        numLivros = 4;
    }

    @FXML
    void actionTopTres(ActionEvent event) {
        numLivros = 3;
    }

    @FXML
    void actionTopDois(ActionEvent event) {
        numLivros = 2;
    }

    @FXML
    void actionTopUm(ActionEvent event) {
        numLivros = 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numLivros = 10;
        setReportInformation();
    }

    public void setReportInformation() {
        qntLivrosEmprestados.setText(
                String.valueOf(Relatorio.numLivrosEmprestados())
        );
        qntdLivrosReservados.setText(
                String.valueOf(Relatorio.numLivrosReservados())
        );
        qntdLivrosAtraso.setText(
                String.valueOf(Relatorio.numLivrosAtrasados())
        );
        feedBooksList();
    }
    public void feedBooksList() {
        ObservableList<Livro> list = FXCollections.observableArrayList(
                Relatorio.livrosMaisPopulares(numLivros)
        );
        renderList(list);
    }

    private void renderList(ObservableList<Livro> list) {
        int size = list.size();
        booksList.getChildren().clear();
        for(int i = 0; i < size; i++){
            Livro book = list.get(i);
            renderRow(book);
        }

    }

    private void renderRow(Livro book){

        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("book-row-view.fxml"));
            Node node = loader.load();
            BookRowController bookRowCtrl = loader.getController();
            bookRowCtrl.setTitle(book.getTitulo());

            booksList.getChildren().add(node);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
