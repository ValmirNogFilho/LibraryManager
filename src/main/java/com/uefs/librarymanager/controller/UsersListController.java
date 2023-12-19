package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersListController implements Initializable {

    private ObservableList<Usuario> list;
    private MenuItem criteria;

    @FXML
    private Text registerUserBtn;

    @FXML
    private Text reportBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchInput;

    @FXML
    private TableView<Usuario> usersTable;

    @FXML
    private TableColumn<Usuario, String> userNames;

    @FXML
    private TableColumn<Usuario, String> userOccupations;

    private TableColumn<Usuario, Button> options;

    @FXML
    private MenuButton criteriaBtn;

    @FXML
    private MenuItem menuItemEmail;

    @FXML
    private MenuItem menuItemID;

    @FXML
    private MenuItem menuItemName;

    @FXML
    void byEmail(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
    }

    @FXML
    void byID(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
    }

    @FXML
    void byName(ActionEvent event) {
        criteria = (MenuItem) event.getSource();
    }

    @FXML
    void openReportPage(MouseEvent event) {

    }

    @FXML
    void openUserRegisterPage(MouseEvent event) {

    }

    @FXML
    void searchClick(ActionEvent event) {
        String id = searchInput.getText();
        if(criteria == menuItemID){
            try{
                list = FXCollections.observableArrayList(DAO.getLeitorDAO().findById(id));
            }catch (UsuarioException e){
                System.out.println("nao achou nao");
            }
            try{
                list = FXCollections.observableArrayList(DAO.getOperadorDAO().findById(id));
            }catch (UsuarioException e){
                System.out.println("nao achou nao");
            }
        }
        renderTable();

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
        list = feedList();
        renderTable();
    }

    private void renderTable(){
        userNames.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
        userOccupations.setCellValueFactory(new PropertyValueFactory<Usuario, String>("cargo"));

        usersTable.setItems(list);
    }
}
