package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.MainApplication;
import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Administrador;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileAdmController{

    @FXML
    private Button btnDel;

    @FXML
    private Button btnbloqueador;

    @FXML
    private ImageView capaUsuario;

    @FXML
    private Button editorSenha;

    @FXML
    private Label labelCargo;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelEndereco;

    @FXML
    private Label labelId;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelTel;

    @FXML
    private Label nomeLabel;

    @FXML
    private TextField numeroEmprestimosF;

    @FXML
    private ImageView perfilUsário;

    @FXML
    private TextField satusUser;

    @FXML
    private Button backBtn;

    @FXML
    private TextField nomeHeader;

    @FXML
    private Button btnEmprestimos;

    private Usuario user;

    private boolean isBlocked;

    public void setUser(Usuario user){
        this.user = user;
        setUserInformation(user);
        disableButtonsIfNotLeitor();
        capaUsuario.setImage(
                new Image(MainApplication.class.getResource(
                        "/css/capaHogwats.jpg").toExternalForm()
                ));
    }

    private void setUserInformation(Usuario user) {
        nomeHeader.setText(user.getNome());
        nomeLabel.setText("Nome: " + user.getNome());
        labelId.setText("ID: " + user.getId());
        labelCargo.setText("Cargo: " + user.getCargo().toString());
        labelNome.setText("Empréstimos de " + user.getNome());
        labelTel.setText("Telefone: " + user.getTelefone());
        perfilUsário.setImage(new Image("file:" + user.getUrlProfileImage()));
        if (user instanceof Leitor){
            isBlocked = ((Leitor) user).getStatus().equals(statusLeitor.BLOQUEADO);

            satusUser.setText(
                    ((Leitor) user).getStatus().toString()
            );
            numeroEmprestimosF.setText( String.valueOf(
                            DAO.getEmprestimoDAO().qtdEmprestimosEmAndamentoDe(
                                    (Leitor) user
                            )
                    )
            );
            btnbloqueador.setText(isBlocked ? "Desbloquear" : "Bloquear");
        }
        else
            satusUser.setPromptText(user.getCargo().toString());
    }

    private void disableButtonsIfNotLeitor(){
        boolean isLeitor = (user.getCargo().equals(cargoUsuario.LEITOR));
        btnbloqueador.setDisable(!isLeitor);
        btnEmprestimos.setVisible(isLeitor);
    }



    @FXML
    void actionBtnDel(ActionEvent event) {
        boolean wantsToDeleteUser = Alerter.confirmationAlert("Deseja excluir esse usuário?",
                "Deseja excluir esse usuário?",
                "Escolha 'OK' para excluir "+ user.getNome() +
                        " ou 'Cancelar' para cancelar a operação."
        );

        if(wantsToDeleteUser){
            deleteUser();
            Alerter.warningAlert("Sucesso!", "Sucesso!", "Exclusão de " +
                    user.getNome() + " (ID: "+ user.getId() +") realizada com sucesso.");
        }
        redirectUpdatedUsersListPage();
    }

    @FXML
    void goBackToUsersList(ActionEvent event) {
        redirectUpdatedUsersListPage();
    }

    private ArrayList<Usuario> generateAllUsersList(){
        ArrayList<Usuario> allUsers = new ArrayList<>();
        allUsers.addAll(DAO.getLeitorDAO().findMany());
        allUsers.addAll(DAO.getOperadorDAO().findMany());
        return allUsers;
    }


    @FXML
    void actionEditorSenha(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Atualizar Senha");
        dialog.setHeaderText("Digite a nova senha para "+ user.getNome()+ ":");
        dialog.setContentText("Nova senha:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(password -> {
            try {
                updatePassword(password);
            } catch (UsuarioException e) {
                Alert invalidPassword = new Alert(Alert.AlertType.WARNING);
                invalidPassword.setTitle("Atenção");
                invalidPassword.setContentText("Senha inválida! Apenas senhas de 4 dígitos numéricos são permitidas");
                invalidPassword.show();
            }
        });

    }

    private void updatePassword(String password) throws UsuarioException {
        user.setSenha(password);
        switch (user.getCargo()){
            case LEITOR -> DAO.getLeitorDAO().update((Leitor) user);
            case ADMINISTRADOR, BIBLIOTECARIO -> DAO.getOperadorDAO().update(user);
        }
    }


    @FXML
    void actionNumeroEmprestimosF(ActionEvent event) {

    }

    @FXML
    void actionStatusUser(ActionEvent event) {

    }

    @FXML
    void actionbloqueador(ActionEvent event) {
        isBlocked = ((Leitor) user).getStatus().equals(statusLeitor.BLOQUEADO);
        if (isBlocked) {
            btnbloqueador.setText("Bloquear");
            unblock();
        }
        else {
            btnbloqueador.setText("Desbloquear");
            block();
        }

        satusUser.setText(((Leitor) user).getStatus().toString());

    }

    private void block() {
        Administrador adm = (Administrador) Session.getUserInSession();
        adm.bloquearLeitor((Leitor) user);
    }

    private void unblock(){
        Administrador adm = (Administrador) Session.getUserInSession();
        adm.desbloquearLeitor((Leitor) user);
    }

    private void redirectUpdatedUsersListPage() {
        try {
            Page page = WindowManager.getNewCreatedPageController("users-list-view.fxml");
            UsersListController usersListCtrl = (UsersListController) page.controller();
            usersListCtrl.setUserRowUrl("user-row-view.fxml");
            usersListCtrl.setListAndRender(generateAllUsersList());
            WindowManager.openPageWithMainPaneId(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteUser(){
        switch (user.getCargo()){
            case LEITOR -> DAO.getLeitorDAO().delete((Leitor) user);
            case BIBLIOTECARIO, ADMINISTRADOR -> DAO.getOperadorDAO().delete(user);
        }
    }


    public void actionBtnEmprestimos(ActionEvent event) throws IOException {

        VBox vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vbox);
        Pane pane = new Pane(scrollPane);

        renderList(vbox, DAO.getEmprestimoDAO().findByLeitor((Leitor) user));

        Stage stage = new Stage();
        Scene scene = new Scene(pane, 577, 400);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Empréstimos de " + user.getNome());
        stage.show();
    }

    private void renderList(VBox borrowingList, List<Emprestimo> list) {
        borrowingList.getChildren().clear();
        list.forEach(
                (emprestimo) -> renderRow(emprestimo, borrowingList)
        );

    }

    private void renderRow(Emprestimo emprestimo, VBox vBox){
        try{
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("borrow-row-view.fxml"));
            Node node = loader.load();
            BorrowRowController borrowRowCtrl = loader.getController();
            borrowRowCtrl.setEmprestimo(emprestimo);

            vBox.getChildren().add(node);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
