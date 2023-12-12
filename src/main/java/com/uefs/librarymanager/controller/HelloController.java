package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class HelloController {

    private String cargo;
    @FXML
    private TextField IDacesso;

    @FXML
    private TextField Senha;

    @FXML
    private RadioButton btnAdm;

    @FXML
    private RadioButton btnBibliotecario;

    @FXML
    private RadioButton btnConvidado;

    @FXML
    private RadioButton btnLeitor;

    @FXML
    private Button btnLogin;

    @FXML
    void btnAdmAction(ActionEvent event) {
        cargo = "Administrador";
    }

    @FXML
    void btnBibliotecarioAction(ActionEvent event) {
        cargo = "Bibliotecário";
    }

    @FXML
    void btnLeitorAction(ActionEvent event) {
        cargo = "Leitor";
    }


    @FXML
    void btnConvidadoAction(ActionEvent event) {
        cargo = "Convidado";
    }

    @FXML
    void btnLoginAction(ActionEvent event) throws UsuarioException {
        Usuario obj = null;
        String senha = Senha.getText();
        String id = IDacesso.getText();

        if(cargo.equals("Administrador") || cargo.equals("Bibliotecario")){
            obj = DAO.getOperadorDAO().findById(id);
        } else if (cargo.equals("Leitor")) {
            obj = (Leitor) DAO.getLeitorDAO().findById(id);
        }
        if(obj.getSenha().equals(senha)){
            System.out.println(obj);
        }
        else throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
    }

}
