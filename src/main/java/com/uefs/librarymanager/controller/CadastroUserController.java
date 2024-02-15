package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroUserController implements Initializable {

    @FXML
    private ImageView FotoUsuario;

    @FXML
    private RadioButton btnGerarId;

    @FXML
    private MenuButton btnTipoUsuario;

    @FXML
    private ImageView capaUsuario;

    @FXML
    private TextField cxEmail;

    @FXML
    private TextField cxEndereço;

    @FXML
    private TextField cxNomeUsuario;

    @FXML
    private TextField cxTelefone;

    @FXML
    private TextField cxPassword;

    private String defaultCoverUrl = getClass().getResource("/img/prophile-photos/profile-template.jpg").toExternalForm();

    private TextField[] allTextFields;

    @FXML
    void acionBtnTipoUsuario(ActionEvent event) {

    }

    @FXML
    void actionBtnGerarId(ActionEvent event) {

    }

    @FXML
    void actionCxEndereço(ActionEvent event) {

    }

    @FXML
    void actionCxNomeUsusario(ActionEvent event) {

    }

    @FXML
    void actionCxTelefone(ActionEvent event) {

    }

    @FXML
    void actioncxEmail(ActionEvent event) {

    }

    @FXML
    void actionRegister(ActionEvent event) {
        TextField missingDataTextField = assertMissingTextFields();

        if(missingDataTextField != null){
            missingDataAlert(missingDataTextField.getPromptText());
            return;
        }

    }

    private TextField assertMissingTextFields() {
        for (TextField textField: allTextFields) {
            if (textField.getText().isEmpty()) return textField;
        }
        return null;
    }

    private void missingDataAlert(String componentName) {
        alert("Dados obrigatórios faltando!", "Dados obrigatórios faltando!",
                componentName + "está faltando. Por favor, preencha esse dado");
    }

    private void alert(String title, String header, String content) {
        Alert confirmationDialog = new Alert(Alert.AlertType.WARNING);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(header);
        confirmationDialog.setContentText(content);

        confirmationDialog.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FotoUsuario.setImage(new Image(
                defaultCoverUrl
        ));

        allTextFields = new TextField[]{cxEmail, cxPassword, cxEndereço, cxTelefone, cxNomeUsuario};
    }

}
