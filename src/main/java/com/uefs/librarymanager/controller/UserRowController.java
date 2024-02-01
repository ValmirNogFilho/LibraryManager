package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UserRowController {


    @FXML
    private HBox hBox;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label occupation;

    @FXML
    private Button profileBtn;

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
        Usuario user = findById(id.getText());
        openProfile(user);
    }

    @FXML
    void onHover(MouseEvent event) {
       hBox.setStyle("-fx-background-color: #5356d6;");
    }

    @FXML
    void outHover(MouseEvent event) {
       hBox.setStyle("-fx-background-color: #f4f4f4;");
    }

    private Usuario findById(String id){
        try {
            return DAO.getOperadorDAO().findById(id);
        } catch (Exception ignored) {}

        try {
            return DAO.getLeitorDAO().findById(id);
        } catch (Exception ignored) {
            return null;
        }
    }

    void openProfile(Usuario user){
        try{
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("profile-adm.fxml"));
            Parent profileView = loader.load();
            ProfileAdmController profileAdmCtrl = loader.getController();

            profileAdmCtrl.setUser(user);
            Stage profileStage = new Stage();
            Scene scene = new Scene(profileView);
            profileStage.setResizable(false);
            profileStage.setScene(scene);
            profileStage.show();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
