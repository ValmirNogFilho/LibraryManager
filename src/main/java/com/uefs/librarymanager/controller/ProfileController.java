package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Session;
import com.uefs.librarymanager.utils.statusLeitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField nomeHeader;

    @FXML
    private ImageView capaUsuario;

    @FXML
    private TextField caxaMinhasReservas;

    @FXML
    private Label labelCargo;

    @FXML
    private Label labelEndereco;

    @FXML
    private Label labelId;

    @FXML
    private Label labelTel;

    @FXML
    private Label nomeLabel;

    @FXML
    private TextField numeroEmprestimosMeus;

    @FXML
    private ImageView perfilUsário;

    @FXML
    private TextField satusUser;

    private Leitor user;

    @FXML
    void actionMinhasReservas(ActionEvent event) {

    }

    @FXML
    void actionNumeroEmprestimosMeus(ActionEvent event) {

    }

    @FXML
    void actionStatusUser(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = (Leitor) Session.getUserInSession();
        setUserInformation(user);
    }

    private void setUserInformation(Leitor user) {
        nomeHeader.setText(user.getNome());
        nomeLabel.setText("Nome: " + user.getNome());
        labelId.setText("ID: " + user.getId());
        labelCargo.setText("Cargo: " + user.getCargo().toString());
        labelTel.setText("Telefone: " + user.getTelefone());
        satusUser.setText(user.getStatus().toString());
        labelEndereco.setText(user.getEndereco());
        numeroEmprestimosMeus.setText(
                String.valueOf(DAO.getEmprestimoDAO().qtdEmprestimosEmAndamentoDe(user))
        );
        caxaMinhasReservas.setText(
                String.valueOf(user.getNumReservas())
        );
    }
}
