package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Page;
import com.uefs.librarymanager.utils.Session;
import com.uefs.librarymanager.utils.WindowManager;
import com.uefs.librarymanager.utils.cargoUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private static cargoUsuario cargo;
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

    public static Stage mainPage;

    ToggleGroup toggleGroup;

    @FXML
    private Label warningText;

    private String screenToBeRendered;

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
    void btnLoginAction(ActionEvent event) {
        Usuario user = null;
        String senha = Senha.getText();
        String id = IDacesso.getText();
        screenToBeRendered = filterScreenByOccupation();
        if (!cargo.equals(cargoUsuario.CONVIDADO))
            try {
                user = login(id, senha);
                Session.loginUser(user);
                redirectHomePage();
            }
            catch (UsuarioException e) {
                warningText.setText(e.getMessage());
            }
        else {
            Session.loginUser(user);
            redirectHomePage();
        }
    }

    /**
     * Este método é responsável pela aba do login no sistema. Nele é feita uma verificação para identificar se o
     * usuário é administrador, bibliotecário ou leitor. Ao usuário ser identificado a partir do seu ID, é feita uma
     * verificação de senha, caso a senha esteja incorreta, a exceção  SENHA_INVALIDA é ativada informando ao usuário
     * que a senha dele difere da senha feita no cadastro.
     *
     * @param id
     * @param senha
     * @throws UsuarioException
     */
    private Usuario login(String id, String senha) throws UsuarioException {
        Usuario obj;

        if(id.isEmpty() || senha.isEmpty()) {
            if(cargo == cargoUsuario.CONVIDADO)
                return null;
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        }

        obj = filterUserByOccupation(id);

        if(!cargo.equals(obj.getCargo()))
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);

        if(obj == null) return null;

        if(!obj.getSenha().equals(senha))
            throw new UsuarioException(UsuarioException.SENHA_INVALIDA);

        return obj;
    }

    private Usuario filterUserByOccupation(String id) throws UsuarioException {
        switch (cargo){
            case LEITOR -> {
                return DAO.getLeitorDAO().findById(id);
            }
            case BIBLIOTECARIO, ADMINISTRADOR -> {
                return DAO.getOperadorDAO().findById(id);
            }
            default -> {
                return null;
            }
        }
    }

    private String filterScreenByOccupation() {
        switch (cargo){
            case LEITOR -> {
                return "front-page.fxml";
            }
            case BIBLIOTECARIO -> {
                return "bibliotecario-front-page.fxml";
            }
            case ADMINISTRADOR -> {
                return "front-page-adm.fxml";
            }
            case CONVIDADO -> {
                return "front-page.fxml";
            }
            default -> {
                return null;
            }
        }
    }


    private void redirectHomePage() {
        WindowManager.closeAllWindows();
        try {
            Page page = WindowManager.getNewCreatedPageController(screenToBeRendered);
            WindowManager.showPageInNewWindow(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        ObservableList toggles = FXCollections.observableArrayList(btnAdm, btnBibliotecario,
                btnConvidado, btnLeitor);
        toggleGroup.getToggles().addAll(toggles);
        toggleGroup.selectToggle(btnConvidado);

        cargo = cargoUsuario.CONVIDADO;

    }
}
