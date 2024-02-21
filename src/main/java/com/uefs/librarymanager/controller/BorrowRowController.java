package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.Alerter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class BorrowRowController {

    @FXML
    private HBox hBox;

    @FXML
    private Label title;

    @FXML
    private Label status;
    private Emprestimo emprestimo;

    private Leitor user;

    private Livro book;

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
        user = DAO.getLeitorDAO().findByPrimaryKey(emprestimo.getUsuarioId()) ;
        book = DAO.getLivroDAO().findByPrimaryKey(emprestimo.getLivroISBN());
        title.setText(book.getTitulo());
        status.setText(String.valueOf(emprestimo.getStatus()));
    }

    private void openDetails(){
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Alerter.warningAlert("Empréstimo",
                "Empréstimo do livro " + book.getTitulo() + " para " + user.getNome(),

                "STATUS: " + emprestimo.getStatus() + ";\n" +
                        "INÍCIO EM :" + emprestimo.getDataInicio().format(pattern) + ";\n" +
                        "FINAL EM: " + emprestimo.getDataFim().format(pattern) + ";\n" +
                        emprestimo.getNumeroRenovacoes() + " RENOVAÇÕES; \n" +
                        emprestimo.getAtraso() + " DIAS DE ATRASO;"
        );
    }

    public void btnClicked(ActionEvent event) {
        openDetails();
    }

    public void onHover(MouseEvent mouseEvent) {
        hBox.setStyle("-fx-background-color: #5356d6;");
    }

    public void outHover(MouseEvent mouseEvent) {
        hBox.setStyle("-fx-background-color: #f4f4f4;");
    }
}
