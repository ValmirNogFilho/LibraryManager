package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.EmprestimoException;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;
import com.uefs.librarymanager.utils.Alerter;
import com.uefs.librarymanager.utils.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.EventListener;
import java.util.Timer;

public class BookController {

    @FXML
    private Label autorLivro;

    @FXML
    private Button btnReserva;

    @FXML
    private Button btnRenovacao;

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

    @FXML
    private Label tempText;

    @FXML
    private Button detalhesEmprestimoBtn;

    private Livro book;

    private Leitor user;

    private boolean isReserved;

    @FXML
    void reservaAction(ActionEvent event) throws LivroException {
        if (Session.getUserInSession() == null) {
            Alerter.warningAlert("Acesso não permitido", "Acesso não permitido",
                    "Para executar uma reserva, você deve possuir fazer login no sistema.");
            return;
        }
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
            Alerter.warningAlert("Sucesso!", "Sucesso!",
        "Você foi adicionado a fila de reservistas do livro " + book.getTitulo() +  " com sucesso");
        } catch (UsuarioException | LivroException e) {
            Alerter.warningAlert("Erro!", "Erro!", e.getMessage());
        }
    }

    private void cancelarReserva() {
        DAO.getReservaDAO().cancelarReserva(user, book);
        Alerter.warningAlert("Sucesso!", "Sucesso!", "Reserva do livro " + book.getTitulo() +" cancelada com sucesso");
    }

    @FXML
    void renovacaoAction(ActionEvent event) {
        if (Session.getUserInSession() == null) {
            Alerter.warningAlert("Acesso não permitido", "Acesso não permitido",
                    "Para executar uma renovação, você deve possuir fazer login no sistema.");
            return;
        }
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Emprestimo renovado = DAO.getEmprestimoDAO().renovarEmprestimo(user, book);
            Alerter.warningAlert("Sucesso!", "Sucesso!", "Empréstimo renovado com sucesso! Devolva o livro até o dia "
            + renovado.getDataFim().format(pattern));
        } catch (EmprestimoException | LivroException e) {
            Alerter.warningAlert("Operação cancelada!", "Operação cancelada!",  e.getMessage());
        }
    }

    @FXML
    void detalhesEmprestimoAction(ActionEvent event) {
        if (Session.getUserInSession() == null) {
            Alerter.warningAlert("Acesso não permitido", "Acesso não permitido",
                    "Para ver detalhes de um empréstimo, você deve possuir fazer login no sistema.");
            return;
        }
        openDetails(DAO.getEmprestimoDAO().findEmprestimo(user, book));
    }

    public void setBookAndRenderPage(Livro book) throws LivroException {
        this.book = book;
        user = (Leitor) Session.getUserInSession();
        if (user != null) {
            isReserved = alreadyReservedByUser(book);
            updateBtnText();
            if (DAO.getReservaDAO().getQueuePosition(user, book) == 0)
                Alerter.warningAlert("Atenção!", "Atenção!",
                        "Você é o primeiro na fila de reservas desse livro e já está apto para realizar empréstimo.");

        }

        setBookInformation(book);
        imagemLivro.setImage(new Image("file:"+book.getImagemUrl()));
        sinopseLivro.setText(book.getSinopse());
        turnLabelsSelectable();

    }

    private void turnLabelsSelectable() {

        turnLabelSelectable(isbnLivro);
        turnLabelSelectable(autorLivro);
        turnLabelSelectable(localizazaoLivro);
        turnLabelSelectable(categoriaLivro);
        turnLabelSelectable(editoraLivro);

    }

    private void turnLabelSelectable(Label label) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> tempText.setVisible(true)),
                new KeyFrame(Duration.seconds(2), e -> tempText.setVisible(false))
        );
        String original = label.getText();
        String substring = original.substring(original.indexOf(":")+1);
        label.setOnMouseClicked(
                (mouseEvent )-> {
                    copyText(substring);
                    timeline.play();
                }
        );
    }

    private void copyText(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
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
        boolean notValidForReservation = book.getDisponiveis() > 0 || DAO.getReservaDAO().filaVazia(book.getISBN());
        btnReserva.setDisable(notValidForReservation);
        boolean bookNotBorrowed = !bookContainedInUserBorrows();
        btnRenovacao.setDisable(bookNotBorrowed);
        detalhesEmprestimoBtn.setDisable(bookNotBorrowed);

    }

    private boolean bookContainedInUserBorrows() {
        for(Emprestimo emp: DAO.getEmprestimoDAO().findByLeitor(user))
            if (emp.getLivroISBN().equals(book.getISBN())) return true;

        return false;
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

    private void openDetails(Emprestimo emprestimo){
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
}
