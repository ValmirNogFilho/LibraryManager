package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.cargoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroUserController implements Initializable {

    @FXML
    private ImageView fotoUsuario;

    @FXML
    private MenuButton btnTipoUsuario;

    @FXML
    private TextField cxEndereco;

    @FXML
    private TextField cxNomeUsuario;

    @FXML
    private TextField cxTelefone;

    @FXML
    private TextField cxSenha;

    @FXML
    private MenuItem admItem;

    @FXML
    private MenuItem bibItem;

    @FXML
    private MenuItem leitorItem;

    private final String defaultProfileUrl = HelloApplication.class.getResource("/img/profile-photos/profile-template.png").toExternalForm();

    private TextField[] allTextFields;

    private MenuItem criteria = null;

    private File selected;


    @FXML
    void leitorItemAction(ActionEvent event) {
        criteria = leitorItem;
        btnTipoUsuario.setText(criteria.getText());
    }
    @FXML
    void bibItemAction(ActionEvent event) {
        criteria = bibItem;
        btnTipoUsuario.setText(criteria.getText());
    }

    @FXML
    void admItemAction(ActionEvent event) {
        criteria = admItem;
        btnTipoUsuario.setText(criteria.getText());
    }

    @FXML
    void escolherFotoAction(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        selected = fileChooser.showOpenDialog(HelloController.mainPage);
        if(selected != null) {
            String selectedImageUrl = selected.toURI().toURL().toString();
            fotoUsuario.setImage(new Image(selectedImageUrl));
        }
    }

    @FXML
    void actionRegister(ActionEvent event) {
        TextField missingDataTextField = assertMissingTextFields();

        if(missingDataTextField != null){
            missingDataAlert(missingDataTextField.getPromptText());
            return;
        }

        Usuario user = null;
        try {
            cargoUsuario cargo = getCargo();
            if(cargo.equals(cargoUsuario.LEITOR)) {
                registerLeitor((Leitor) user);
            }
            else {
                registerOperador(user, cargo);
            }

            alert("Sucesso!", "Sucesso!", "Cadastro de usuário feito com sucesso!");
            blankAllTextFields();

        } catch (RuntimeException missingCargo) {
            missingDataAlert("cargo");
        } catch (UsuarioException invalidPassword) {
            alert("Erro!", invalidPassword.getMessage(), "por favor, faça uma senha de 4 dígitos numéricos");
        }

    }


    private void registerLeitor(Leitor user) throws UsuarioException {
        if(selected == null)
            user = new Leitor(cxNomeUsuario.getText(), cxEndereco.getText(), cxTelefone.getText());
        else
            user = new Leitor(cxNomeUsuario.getText(), cxEndereco.getText(), cxTelefone.getText(),
                    selected.getName());

        user.setSenha(cxSenha.getText());
        DAO.getLeitorDAO().create(user);
    }

    private void registerOperador(Usuario user, cargoUsuario cargo) throws UsuarioException {
        if(selected == null)
            user = new Usuario(cxNomeUsuario.getText(), cxEndereco.getText(),
                    cxTelefone.getText(), null, cargo);
        else
            user = new Usuario(cxNomeUsuario.getText(), cxEndereco.getText(),
                    cxTelefone.getText(), null, cargo, selected.getName());

        user.setSenha(cxSenha.getText());
        DAO.getOperadorDAO().create(user);
    }

    private void blankAllTextFields() {
        for (TextField textField: allTextFields) {
            textField.setText("");
        }
        fotoUsuario.setImage(new Image(defaultProfileUrl));
    }

    private cargoUsuario getCargo() throws RuntimeException {
        if(criteria == null)
            throw new RuntimeException("Selecione um cargo para o usuário");
        if(criteria.equals(admItem))
            return cargoUsuario.ADMINISTRADOR;
        if(criteria.equals(bibItem))
            return cargoUsuario.BIBLIOTECARIO;
        return cargoUsuario.LEITOR;
    }

    private TextField assertMissingTextFields() {
        for (TextField textField: allTextFields) {
            if (textField.getText().isEmpty()) return textField;
        }
        return null;
    }

    private void missingDataAlert(String componentName) {
        alert("Dados obrigatórios faltando!", "Dados obrigatórios faltando!",
                componentName + " possui um valor inválido. Por favor, preencha esse dado");
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

        fotoUsuario.setImage(new Image(
                defaultProfileUrl
        ));

        allTextFields = new TextField[]{cxSenha, cxEndereco, cxTelefone, cxNomeUsuario};
        setPromptTexts();
    }

    private void setPromptTexts() {
        cxSenha.setPromptText("Senha");
        cxEndereco.setPromptText("Endereço");
        cxNomeUsuario.setPromptText("Nome");
        cxTelefone.setPromptText("Telefone");
    }

}
