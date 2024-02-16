package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class UsersListController{

    @FXML
    private MenuButton criteriaBtn;

    @FXML
    private Label logoutBtn;

    @FXML
    private MenuItem menuItemID;

    @FXML
    private MenuItem menuItemName;

    @FXML
    private Text registerUserBtn;

    @FXML
    private Text reportBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox usersList;

    private String userRowUrl;

    private ObservableList<Usuario> list;
    private MenuItem criteria;

    public void setBook(Livro book) {
        this.book = book;
    }

    private Livro book;

    @FXML
    void byID(ActionEvent event) {

        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Busque um id...");
    }

    @FXML
    void byName(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
        searchInput.setPromptText("Busque um nome...");
    }

    @FXML
    void searchClick(ActionEvent event) {
        String text = searchInput.getText();

        if(criteria == menuItemID) {
            String id = text;
            try {
                list = FXCollections.observableArrayList(DAO.getLeitorDAO().findById(id));
            } catch (UsuarioException e) {}
            try {
                list = FXCollections.observableArrayList(DAO.getOperadorDAO().findById(id));
            } catch (UsuarioException e) {}

        }
        else if(criteria == menuItemName){
            String name = text;
            list = FXCollections.observableArrayList(DAO.getLeitorDAO().findByName(name));
            list.addAll(DAO.getOperadorDAO().findByName(name));

        }

        renderList();

    }

    public void setListAndRender(List<Usuario> list) {
        criteria = menuItemName;
        this.list = FXCollections.observableArrayList(list);
        renderList();
    }



    private void renderList() {
        int size = list.size();
        usersList.getChildren().clear();
        for(int i = 0; i < size; i++){
            Usuario user = list.get(i);
            renderRow(user);
        }

    }

    private void renderRow(Usuario user){
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(userRowUrl));
            Node node = loader.load();
            UserRow userCtrl = loader.getController();
            userCtrl.setUser(user);
            userCtrl.setBook(book);
            usersList.getChildren().add(node);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setUserRowUrl(String userRowUrl) {
        this.userRowUrl = userRowUrl;
    }
}
