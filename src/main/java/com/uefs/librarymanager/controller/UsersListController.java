package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersListController implements Initializable {

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

    private ObservableList<Usuario> list;
    private MenuItem criteria;

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
    void openReportPage(MouseEvent event) {
    }

    @FXML
    void openUserRegisterPage(MouseEvent event) {

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


    private ObservableList<Usuario> feedList() {

        ArrayList<Usuario> allUsers = new ArrayList<>();
        allUsers.addAll(DAO.getLeitorDAO().findMany());
        allUsers.addAll(DAO.getOperadorDAO().findMany());

        return FXCollections.observableArrayList(
            allUsers
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criteria = menuItemName;
        list = feedList();
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-row-view.fxml"));
            Node node = loader.load();
            UserRowController userCtrl = loader.getController();
            userCtrl.setId(user.getId());
            userCtrl.setName(user.getNome());
            userCtrl.setOccupation(String.valueOf(user.getCargo()));
            usersList.getChildren().add(node);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
