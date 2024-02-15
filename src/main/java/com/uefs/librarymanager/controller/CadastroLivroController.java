package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;

public class CadastroLivroController implements Initializable {

    @FXML
    private ImageView capaLivro;

    @FXML
    private TextField cxISBN;

    @FXML
    private TextField cxLocalizacao;

    @FXML
    private TextField cxNomeAutor;

    @FXML
    private TextField cxNomeCategoria;

    @FXML
    private TextField cxNomeEditora;

    @FXML
    private TextField cxNomeLivro;

    @FXML
    private TextField cxQuantidade;

    @FXML
    private TextField cxAnoPublicacao;

    @FXML
    private TextArea sinopseLivro;

    @FXML
    private Button registerBtn;

    private String defaultCoverUrl = getClass().getResource("/img/book-covers/template.jpg").toExternalForm();
    @FXML
    void openFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selected = fileChooser.showOpenDialog(HelloController.mainPage);
        try {
            File copy = copy(selected);
            capaLivro.setImage(new Image(copy.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerAction(ActionEvent event) {
        TextField missingDataTextField = assertMissingTextFields();

        if(missingDataTextField != null){
            missingDataAlert(missingDataTextField.getId());
            return;
        }

        Livro book;

        if(sinopseLivro.getText().isEmpty() || capaLivro.getImage().getUrl().equals(
                getClass().getResource("/img/book-covers/template.jpg").toExternalForm()
        ) || capaLivro.getImage().getUrl().equals(defaultCoverUrl))
            book = new Livro(cxNomeLivro.getText(), cxNomeAutor.getText(), cxNomeEditora.getText(), cxISBN.getText(),
                        Integer.parseInt(cxAnoPublicacao.getText()), cxLocalizacao.getText(), cxNomeCategoria.getText(),
                    Integer.parseInt(cxQuantidade.getText()));
        else
            book = new Livro(cxNomeLivro.getText(), cxNomeAutor.getText(), cxNomeEditora.getText(), cxISBN.getText(),
                    Integer.parseInt(cxAnoPublicacao.getText()), cxLocalizacao.getText(), cxNomeCategoria.getText(),
                    Integer.parseInt(cxQuantidade.getText()), sinopseLivro.getText(), capaLivro.getImage().getUrl());

        DAO.getLivroDAO().create(book);
        alert("Operação concluída!", "Operação concluída!", "Livro" + book.getTitulo() + "cadastrado com sucesso!");

    }

    private TextField assertMissingTextFields() {
        if(cxNomeLivro.getText().isEmpty()) return cxNomeLivro;
        if(cxNomeAutor.getText().isEmpty()) return cxNomeAutor;
        if(cxISBN.getText().isEmpty()) return cxISBN;
        if(cxNomeEditora.getText().isEmpty()) return cxNomeEditora;
        if(cxNomeCategoria.getText().isEmpty()) return cxNomeCategoria;
        if(cxLocalizacao.getText().isEmpty()) return cxLocalizacao;
        if(cxQuantidade.getText().isEmpty()) return cxQuantidade;
        if(cxAnoPublicacao.getText().isEmpty()) return cxAnoPublicacao;
        return null;

    }

    private void missingDataAlert(String componentName) {
        alert("Dados obrigatórios faltando!", "Dados obrigatórios faltando!",
                componentName + "está vazio. Por favor, preencha esse dado");
    }

    private void alert(String title, String header, String content) {
        Alert confirmationDialog = new Alert(Alert.AlertType.WARNING);
        confirmationDialog.setTitle(title);
        confirmationDialog.setHeaderText(header);
        confirmationDialog.setContentText(content);

        confirmationDialog.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        capaLivro.setImage(new Image(
                defaultCoverUrl
        ));
    }

    private File copy(File file) throws IOException {
        File src = new File(file.getPath());
        File dest = new File(getClass().getResource("/img/book-covers/") + file.getName());

        Files.copy(src.toPath(), dest.toPath());
        return dest;
    }
}
