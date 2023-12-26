package com.uefs.librarymanager.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class UserRowController {

    @FXML
    private ContextMenu ctxtMenu;

    @FXML
    private MenuItem blockItem;

    @FXML
    private MenuItem excludeItem;

    @FXML
    private MenuItem profileItem;

    @FXML
    private HBox hBox;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label occupation;

    @FXML
    private Button optionsBtn;

    public Label getId() {
        return id;
    }

    public void setId(String id) {
        this.id.setText(id);
    }

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation.setText(occupation);
    }

    @FXML
    void btnClicked(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        // Obtém a cena associada ao botão
        Scene scene = sourceButton.getScene();
        double yInScene = sourceButton.localToScene(0, 0).getY();
        double yOnScreen = scene.getWindow().getY() + yInScene;
        Button btn = (Button) event.getSource();
        ctxtMenu.show(btn, 947, yOnScreen);
    }
    @FXML
    void onHover(MouseEvent event) {
       hBox.setStyle("-fx-background-color: #5356d6;");
    }

    @FXML
    void outHover(MouseEvent event) {
       hBox.setStyle("-fx-background-color: #f4f4f4;");
    }

}
