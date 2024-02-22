package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.utils.Alerter;
import com.uefs.librarymanager.utils.FileUtils;
import com.uefs.librarymanager.utils.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
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

    private String defaultCoverUrl = "file:"+ Livro.BOOK_COVERS_DIRECTORY + Livro.TEMPLATE_COVER;

    private File defaultImage = new File(Livro.BOOK_COVERS_DIRECTORY+Livro.TEMPLATE_COVER);


    @FXML
    void openFile(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        selected = fileChooser.showOpenDialog(LoginController.mainPage);
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

        if(!cxAnoPublicacao.getText().matches("\\d+")){
            Alerter.warningAlert("Erro!", "Dados incompatíveis",
                    "O ano de publicação deve conter apenas números");
            return;
        }

        if(!cxQuantidade.getText().matches("\\d+")){
            Alerter.warningAlert("Erro!", "Dados incompatíveis",
                    "O número de exemplares disponíveis deve conter apenas números");
            return;
        }


        try {
            getBookData();
        } catch (IOException e) {
            Alerter.warningAlert("Erro!", "Cadastro não realizado",
                "Ocorreu um erro na tentativa de salvar a imagem, tente novamente.");
            return;
        }
        Alerter.warningAlert("Operação concluída!", "Operação concluída!",
                "Livro " + book.getTitulo() + " atualizado com sucesso!");
        WindowManager.closeThisWindow(event);
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
        Alerter.warningAlert("Dados obrigatórios faltando!", "Dados obrigatórios faltando!",
                componentName + " está faltando. Por favor, preencha esse dado");
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
        capaLivro.setImage(new Image("file:"+book.getImagemUrl()));
        cxISBN.setText(String.valueOf(book.getDisponiveis()));
        cxAnoPublicacao.setText(String.valueOf(book.getAnoDePublicacao()));
    }

    private void getBookData() throws IOException {
        book.setTitulo(cxNomeLivro.getText());
        book.setAutor(cxNomeAutor.getText());
        book.setCategoria(cxNomeCategoria.getText());
        book.setEditora(cxNomeEditora.getText());
        book.setDisponiveis(Integer.parseInt(cxQuantidade.getText()));
        book.setAnoDePublicacao(Integer.parseInt(cxAnoPublicacao.getText()));

        selected = selected == null ? defaultImage : selected;

        FileUtils.copiarImagemPara(selected, Livro.BOOK_COVERS_DIRECTORY);

        book.setImagemUrl(Livro.BOOK_COVERS_DIRECTORY + selected.getName());
        book.setISBN(cxISBN.getText());
        book.setLocalizacao(cxLocalizacao.getText());
        book.setSinopse(sinopseLivro.getText());

        DAO.getLivroDAO().update(book);
    }

}
