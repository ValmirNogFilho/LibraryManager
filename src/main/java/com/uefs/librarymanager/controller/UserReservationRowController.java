package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UserReservationRowController implements UserRow{

    @FXML
    private HBox hBox;
    @FXML
    private Label name;
    private Usuario user;
    @Override
    public void setUser(Usuario user) {
        this.user = user;
        name.setText(user.getNome());
    }

    @FXML
    void btnClicked(ActionEvent event){
        System.out.println("foi");
    }

    @FXML
    void onHover(ActionEvent event){
        hBox.setStyle("-fx-background-color: #5356d6;");
    }
    @FXML
    void outHover(ActionEvent event){
        hBox.setStyle("-fx-background-color: #f4f4f4;");
    }

}
