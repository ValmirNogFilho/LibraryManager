package com.uefs.librarymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BookRowController {

    @FXML
    private Label title;

    @FXML
    private Button openBookPage;

    public void setTitle (String title) {
        this.title.setText(title);
    }


    @FXML
    void btnClicked(ActionEvent event) {

    }

    @FXML
    void onHover(MouseEvent event) {

    }

    @FXML
    void outHover(MouseEvent event) {

    }

}
