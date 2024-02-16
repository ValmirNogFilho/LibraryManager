package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.List;

public class UserReservationRowController implements UserRow{

    @FXML
    private HBox hBox;

    @FXML
    private Label name;

    @FXML
    private Button profileBtn;

    protected Leitor user;

    protected Livro book;

    @Override
    public void setUser(Usuario user) {
        this.user = (Leitor) user;
        name.setText(user.getNome());
    }

    @Override
    public void setBook(Livro book) {
        this.book = book;
    }

    @FXML
    void btnClicked(ActionEvent event) {
        Reserva reserva = findReserva();

        if(wantsToBorrow() && reserva != null) {
            try {
                DAO.getReservaDAO().registrarEmprestimoPorReserva(reserva);
                alert("Sucesso!", "Sucesso!", "Empréstimo por reserva cadastrado com sucesso!");
            } catch (UsuarioException | LivroException e) {
                alert("Erro!", "Cadastro não executado", e.getMessage());
            }
        }
        else {
            alert("Operação cancelada!", "Cadastro não executado", "Operação cancelada.");
        }

    }

    private Reserva findReserva() {
        List<Reserva> reservasDoLeitor = DAO.getReservaDAO().findByLeitor(user);
        for (Reserva res: reservasDoLeitor) {
            if (res.getIdUsuario().equals(user.getId())) {
                return res;
            }
        }
        return null;
    }



    protected boolean wantsToBorrow() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Sair");
        confirmationDialog.setHeaderText("Deseja registrar o empréstimo por reserva do livro " + book.getTitulo() + " para " + user.getNome() +"?" );
        confirmationDialog.setContentText("Escolha 'OK' para sair");

        confirmationDialog.showAndWait();

        return confirmationDialog.getResult() == ButtonType.OK;
    }

    protected void alert(String title, String header, String content) {
        Alert confirmationDialog = new Alert(Alert.AlertType.WARNING);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(header);
        confirmationDialog.setContentText(content);

        confirmationDialog.show();
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
