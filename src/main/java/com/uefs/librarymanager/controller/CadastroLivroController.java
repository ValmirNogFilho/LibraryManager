package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

    private TextField[] allTextFields;

    private String selectedImageUrl;

    private File selected;

    private String defaultCoverUrl = "file:"+Livro.BOOK_COVERS_DIRECTORY + Livro.TEMPLATE_COVER;
    @FXML
    void openFile(ActionEvent event) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleciona uma capa:");
        fileChooser.setInitialDirectory(new File("C:\\"));
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("jpg_Image", "*.jpg", "*.jpeg"); //Add as you need
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("png_Image", "*.png"); //Add as you need
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("GIF", "*.gif"); //Add as you need
        fileChooser.getExtensionFilters().addAll(ex, ex1, ex2);

        selected = fileChooser.showOpenDialog(HelloController.mainPage);
        if(selected != null) {
            selectedImageUrl = selected.toURI().toURL().toString();
            capaLivro.setImage(new Image(selectedImageUrl));
        }
    }
    @FXML
    void registerAction(ActionEvent event) {
        TextField missingDataTextField = assertMissingTextFields();

        if(missingDataTextField != null){
            missingDataAlert(missingDataTextField.getPromptText());
            return;
        }

        Livro book;

        if(!cxAnoPublicacao.getText().matches("\\d+")){
            alert("Erro!", "Dados incompatíveis", "O ano de publicação deve conter apenas números");
            return;
        }

        if(!cxQuantidade.getText().matches("\\d+")){
            alert("Erro!", "Dados incompatíveis",
                    "O número de exemplares disponíveis deve conter apenas números");
            return;
        }

        if(selectedImageUrl == null ||
                selectedImageUrl.equals(defaultCoverUrl)) {

            book = new Livro(cxNomeLivro.getText(), cxNomeAutor.getText(), cxNomeEditora.getText(), cxISBN.getText(),
                        Integer.parseInt(cxAnoPublicacao.getText()), cxLocalizacao.getText(), cxNomeCategoria.getText(),
                    Integer.parseInt(cxQuantidade.getText()), "");
        }
        else{

            book = new Livro(cxNomeLivro.getText(), cxNomeAutor.getText(), cxNomeEditora.getText(), cxISBN.getText(),
                    Integer.parseInt(cxAnoPublicacao.getText()), cxLocalizacao.getText(), cxNomeCategoria.getText(),
                    Integer.parseInt(cxQuantidade.getText()), sinopseLivro.getText(), selected.getName());
            try {
                FileUtils.copiarImagemPara(selected, Livro.BOOK_COVERS_DIRECTORY);
            } catch (IOException e) {
                alert("Erro!", "Cadastro não realizado",
                        "Ocorreu um erro na tentativa de salvar a imagem, tente novamente.");
                return;
            }
        }

        DAO.getLivroDAO().create(book);
        alert("Operação concluída!", "Operação concluída!", "Livro " + book.getTitulo() + " cadastrado com sucesso!");
        blankAllFields();
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
                componentName + " está faltando. Por favor, preencha esse dado");
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


}
