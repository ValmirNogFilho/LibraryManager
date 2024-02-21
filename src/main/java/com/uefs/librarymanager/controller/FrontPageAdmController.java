package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Administrador;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Alerter;
import com.uefs.librarymanager.utils.Page;
import com.uefs.librarymanager.utils.Session;
import com.uefs.librarymanager.utils.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FrontPageAdmController implements Initializable {

    @FXML
    private BorderPane borderPaneAdm;

    @FXML
    private Label admName;

    private Administrador adm;

    ArrayList<Usuario> allUsers;

    @FXML
    void actionInicioAdm(ActionEvent event) {
        renderUsersListPage();
    }


    @FXML
    void actionNovoUser(ActionEvent event) {
        WindowManager.openPageInBorderPane("cadastro-user.fxml", borderPaneAdm);
    }


    @FXML
    void actionRelatorio(ActionEvent event) {
        WindowManager.openPageInBorderPane("relatorio.fxml", borderPaneAdm);
    }


    @FXML
    void actionSairAdm(ActionEvent event) {
        if (!(Alerter.wantsToLogoutAlert()))
            return;

        Session.logoutUser();
        WindowManager.closeAllWindows();
        WindowManager.openLoginPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateAllUsersList();
        adm = (Administrador) Session.getUserInSession();
        admName.setText("Bem vinda(o)," + adm.getNome());


    }

    private void generateAllUsersList(){
        allUsers = new ArrayList<>();
        allUsers.addAll(DAO.getLeitorDAO().findMany());
        allUsers.addAll(DAO.getOperadorDAO().findMany());
        renderUsersListPage();
    }

    private void renderUsersListPage() {
        Page page = WindowManager.openPageInBorderPane("users-list-view.fxml", borderPaneAdm);
        UsersListController usersListCtrl = (UsersListController) page.controller();
        usersListCtrl.setUserRowUrl("user-row-view.fxml");
        usersListCtrl.setListAndRender(allUsers);
    }

}
