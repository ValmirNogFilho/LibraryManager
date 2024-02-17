package com.uefs.librarymanager;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Sistema;
import com.uefs.librarymanager.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class HelloApplication extends Application {

    private static final String ULTIMO_ACESSO_FILE_NAME = "ultimo_acesso";

    @Override
    public void start(Stage stage) throws IOException {
        File ultimoAcessoFile = FileUtils.obterInstanciaArquivo(ULTIMO_ACESSO_FILE_NAME);
        LocalDate ultimoAcesso = FileUtils.consultarArquivoLocalDate(ultimoAcessoFile);
        FileUtils.sobreescreverArquivo(ultimoAcessoFile, LocalDate.now());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bem vindo ao Library Manager!");
        stage.setScene(scene);
        stage.show();
        Sistema.atualizarMultas(ultimoAcesso);
        Sistema.atualizarReservas();
    }

    public static void main(String[] args) {
        launch(args);
    }
}