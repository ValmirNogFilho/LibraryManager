package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UserBorrowingRowController extends UserReservationRowController{
    @FXML
    void btnClicked(ActionEvent event) {
        if(wantsToBorrow()) {
            try {
                DAO.getEmprestimoDAO().registrarEmprestimo(user, book);
                alert("Sucesso!", "Sucesso!", "Empréstimo cadastrado com sucesso!");
            } catch (UsuarioException | LivroException e) {
                alert("Erro!", "Cadastro não executado", e.getMessage());
            }
        }
        else {
            alert("Operação cancelada!", "Cadastro não executado", "Operação cancelada.");
        }

    }
}
