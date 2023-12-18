package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.cargoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class HelloController {

    private static cargoUsuario cargo = cargoUsuario.CONVIDADO;
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
        cargo = cargoUsuario.ADMINISTRADOR;
    }

    @FXML
    void btnBibliotecarioAction(ActionEvent event) {
        cargo = cargoUsuario.BIBLIOTECARIO;
    }

    @FXML
    void btnLeitorAction(ActionEvent event) {
        cargo = cargoUsuario.LEITOR;
    }


    @FXML
    void btnConvidadoAction(ActionEvent event) {
        cargo = cargoUsuario.CONVIDADO;
    }

    @FXML
    void btnLoginAction(ActionEvent event) throws UsuarioException {
        Usuario obj = null;
        String senha = Senha.getText();
        String id = IDacesso.getText();

        switch (cargo){
            case LEITOR -> obj = DAO.getLeitorDAO().findById(id);
            case BIBLIOTECARIO, ADMINISTRADOR -> obj = DAO.getOperadorDAO().findById(id);
            default -> System.out.println("entrou como convidado");
        }

        if(obj == null) return;

        if(!cargo.equals(obj.getCargo())){
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        }

        if(!obj.getSenha().equals(senha)){
            throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
        }

        System.out.println(obj);

    }


}
