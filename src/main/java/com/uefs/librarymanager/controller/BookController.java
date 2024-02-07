package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;
import com.uefs.librarymanager.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BookController {

    @FXML
    private Label autorLivro;

    @FXML
    private Button btnReserva;

    @FXML
    private Label editoraLivro;

    @FXML
    private ImageView imagemLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label localizazaoLivro;

    @FXML
    private Label qntdDisponivel;

    @FXML
    private Label categoriaLivro;

    @FXML
    private TextArea sinopseLivro;

    private Livro book;

    private Leitor user;

    private boolean isReserved;

    @FXML
    void reservaAction(ActionEvent event) throws LivroException {
        if(!isReserved){
            reservarLivro();
            isReserved = true;
        }
        else {
            cancelarReserva();
            isReserved = false;
        }
        updateBtnText();
    }


    private void reservarLivro() {
        try {
            DAO.getReservaDAO().registrarReserva(user, book);
            openDialog("Sucesso!",
        "Você foi adicionado a fila de reservistas do livro " + book.getTitulo() +  " com sucesso");
        } catch (UsuarioException | LivroException e) {
            openDialog("Erro!", e.getMessage());
        }
    }

    private void cancelarReserva() {
        DAO.getReservaDAO().cancelarReserva(user, book);
        openDialog("Sucesso!", "Reserva do livro " + book.getTitulo() +" cancelada com sucesso");
    }

    public void setBookAndRenderPage(Livro book) throws LivroException {
        this.book = book;
        user = (Leitor) Session.getUserInSession();
        setBookInformation(book);
        isReserved = alreadyReservedByUser(book);
        updateBtnText();
        imagemLivro.setImage(new Image(getClass().getResource(book.getImagemUrl()).toExternalForm()));
        sinopseLivro.setText(book.getSinopse());
        if (DAO.getReservaDAO().getQueuePosition(user, book) == 0){
            openDialog("Atenção!", "Você é o primeiro na fila de reservas desse livro e já está apto para realizar empréstimo.");


        }
    }


    private void clickShow(ActionEvent event) {

    }

    private boolean alreadyReservedByUser(Livro book) {
        Reserva reserva = DAO.getReservaDAO().findByPrimaryKey(book.getISBN());
        if (reserva == null)
            return false;

        return reserva.getIdUsuario().equals(user.getId());
    }

    private void updateBtnText() throws LivroException {
        if (isReserved) {
            btnReserva.setText("Cancelar reserva");
            return;
        }

        btnReserva.setText("Reservar");
        if (book.getDisponiveis() > 0 || DAO.getReservaDAO().filaVazia(book.getISBN()))
            btnReserva.setDisable(true);
    }


    private void setBookInformation(Livro book) {
        autorLivro.setText("Autor:" + book.getAutor());
        editoraLivro.setText("Editora: " + book.getEditora());
        localizazaoLivro.setText("Localização: " + book.getLocalizacao());
        isbnLivro.setText("ISBN:" + book.getISBN());
        categoriaLivro.setText("Categoria: " + book.getCategoria());
        qntdDisponivel.setText(
                "Exemplares disponíveis: " + book.getDisponiveis()
        );
    }

    public void openDialog(String header, String message){
        Alert warningDialog = new Alert(Alert.AlertType.WARNING);
        warningDialog.setTitle(header);
        warningDialog.setHeaderText(header);
        warningDialog.setContentText(message);

        warningDialog.showAndWait();
    }
}
