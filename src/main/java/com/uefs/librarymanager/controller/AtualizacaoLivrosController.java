package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.HelloApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class AtualizacaoLivrosController implements Initializable {

    @FXML
    private ImageView capaLivro;

    @FXML
    private TextField cxAnoPublicacao;

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
    private Button registerBtn;

    @FXML
    private TextArea sinopseLivro;

    private Livro book;

    private File selected;

    private String selectedImageUrl;

    private TextField[] allTextFields;

    private String defaultCoverUrl = getClass().getResource("/img/book-covers/template.jpg").toExternalForm();

    @FXML
    void openFile(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        selected = fileChooser.showOpenDialog(HelloController.mainPage);
        if(selected != null) {
             selectedImageUrl = selected.toURI().toURL().toString();
            capaLivro.setImage(new Image(selectedImageUrl));
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
        TextField missingDataTextField = assertMissingTextFields();

        if(missingDataTextField != null){
            missingDataAlert(missingDataTextField.getPromptText());
            return;
        }
        getBookData();

        DAO.getLivroDAO().update(book);
        alert("Operação concluída!", "Operação concluída!", "Livro " + book.getTitulo() + " atualizado com sucesso!");
        Stage currentScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentScreen.close();
    }

    private TextField assertMissingTextFields() {
        for (TextField textField: allTextFields) {
            if (textField.getText().isEmpty()) return textField;
        }
        return null;
    }

    private void blankAllFields() {
        for (TextField textField: allTextFields) {
            textField.setText("");
        }
        sinopseLivro.setText("");
        capaLivro.setImage(new Image(defaultCoverUrl));
    }

    private void missingDataAlert(String componentName) {
        alert("Dados obrigatórios faltando!", "Dados obrigatórios faltando!",
                componentName + "está faltando. Por favor, preencha esse dado");
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

        allTextFields = new TextField[]{cxNomeLivro, cxNomeAutor, cxISBN, cxNomeEditora,
                cxNomeCategoria, cxLocalizacao, cxAnoPublicacao, cxQuantidade};
        setPromptTexts();
    }

    private void setPromptTexts() {
        cxNomeLivro.setPromptText("Nome do livro");
        cxNomeAutor.setPromptText("Nome do autor");
        cxISBN.setPromptText("ISBN");
        cxNomeEditora.setPromptText("Nome da editora");
        cxNomeCategoria.setPromptText("Nome da categoria");
        cxLocalizacao.setPromptText("Localização");
        cxAnoPublicacao.setPromptText("Ano de publicação");
        cxQuantidade.setPromptText("Quantidade de exemplares");

    }

    public void setBookAndRender(Livro book) {
        this.book = book;
        setBookData();
    }

    private void setBookData() {
        cxNomeLivro.setText(book.getTitulo());
        cxISBN.setText(book.getISBN());
        cxNomeCategoria.setText(book.getCategoria());
        cxNomeAutor.setText(book.getAutor());
        cxNomeEditora.setText(book.getEditora());
        cxLocalizacao.setText(book.getLocalizacao());
        cxQuantidade.setText(String.valueOf(book.getDisponiveis()));
        sinopseLivro.setText(book.getSinopse());
        capaLivro.setImage(new Image(
                        HelloApplication.class.getResource(book.getImagemUrl()).toExternalForm()
                )
        ); //TODO
        cxISBN.setText(String.valueOf(book.getDisponiveis()));
    }

    private void getBookData() {
        book.setTitulo(cxNomeLivro.getText());
        book.setAutor(cxNomeAutor.getText());
        book.setCategoria(cxNomeCategoria.getText());
        book.setEditora(cxNomeEditora.getText());
        book.setDisponiveis(Integer.parseInt(cxQuantidade.getText()));
        book.setAnoDePublicacao(cxAnoPublicacao.getText().isEmpty()? 0 :  Integer.parseInt(cxAnoPublicacao.getText()));
        book.setImagemUrl(capaLivro.getImage().getUrl()); // TODO
        book.setISBN(cxISBN.getText());
        book.setLocalizacao(cxLocalizacao.getText());
        book.setSinopse(sinopseLivro.getText());
    }

}
