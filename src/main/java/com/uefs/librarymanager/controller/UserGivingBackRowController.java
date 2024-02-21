package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.utils.Alerter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UserGivingBackRowController extends UserReservationRowController {

    @FXML
    void btnClicked(ActionEvent event) {
        if(wantsToDevolve()) {
            try {
                Emprestimo emprestimo = DAO.getEmprestimoDAO().findEmprestimo(user, book);
                DAO.getEmprestimoDAO().devolverLivro(emprestimo);
                hBox.setVisible(false);
                Alerter.warningAlert("Sucesso!", "Sucesso!", "Livro devolvido com sucesso!");
            } catch (UsuarioException | LivroException e) {
                Alerter.warningAlert("Erro!", "Devolução não executada", e.getMessage());
            }
        }
        else {
            Alerter.warningAlert("Operação cancelada!", "Devolução não executada", "Operação cancelada.");
        }

    }

    private boolean wantsToDevolve() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Sair");
        confirmationDialog.setHeaderText("Deseja devolver o livro " + book.getTitulo() + " emprestado para " + user.getNome() +"?" );
        confirmationDialog.setContentText("Escolha 'OK' para confirmar");

        confirmationDialog.showAndWait();

        return confirmationDialog.getResult() == ButtonType.OK;
    }

}
