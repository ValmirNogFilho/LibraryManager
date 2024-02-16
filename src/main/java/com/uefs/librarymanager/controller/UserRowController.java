package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UserRowController implements UserRow{


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

    private Usuario user;



    public Label getId() {
        return id;
    }

    private void setId(String id) {
        this.id.setText(id);
    }

    public Label getName() {
        return name;
    }

    private void setName(String name) {
        this.name.setText(name);
    }

    public Label getOccupation() {
        return occupation;
    }

    private void setOccupation(String occupation) {
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

    @Override
    public void setUser(Usuario user) {
        this.user = user;
        setId(user.getId());
        setName(user.getNome());
        setOccupation(String.valueOf(user.getCargo()));
    }

    private void openProfile(Usuario user){
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
