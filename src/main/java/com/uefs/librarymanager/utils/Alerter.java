package com.uefs.librarymanager.utils;

import com.uefs.librarymanager.exceptions.UsuarioException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public abstract class Alerter {
    private static Alert alert(String title, String header, String content, Alert.AlertType alertType) {
        Alert confirmationDialog = new Alert(alertType);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(header);
        confirmationDialog.setContentText(content);

        return confirmationDialog;
    }

    public static void warningAlert(String title, String header, String content) {
        Alert alert = alert(title, header, content, Alert.AlertType.WARNING);
        alert.show();
    }

    public static boolean confirmationAlert(String title, String header, String content) {
        Alert alert = alert(title, header, content, Alert.AlertType.CONFIRMATION);
        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }

    public static boolean wantsToLogoutAlert() {
        return confirmationAlert("Sair", "Deseja sair?", "Escolha 'OK' para sair");
    }


}
