package com.uefs.librarymanager.utils;

import com.uefs.librarymanager.HelloApplication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public abstract class WindowManager {

    public static Page getNewCreatedPageController(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
        Parent viewPage = loader.load();
        Object controller = loader.getController();

        return new Page(controller, viewPage);
    }

    public static void openLoginPage(){
        Page page;
        try {
            page = getNewCreatedPageController("hello-view.fxml");
            showPageInNewWindow(page);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showPageInNewWindow(Page page){
        Stage stage = new Stage();
        Scene scene = new Scene((Parent) page.viewPage());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static Page openPageInBorderPane(String url, BorderPane borderPaneAdm) {
        Page page;
        try {
            page = getNewCreatedPageController(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPaneAdm.setCenter(
                page.viewPage()
        );

        return page;
    }

    public static void closeAllWindows() {
        ObservableList<Window> windows = Stage.getWindows();
        while (!windows.isEmpty())
            ((Stage) windows.get(0)).close();
    }

    public static void closeThisWindow(ActionEvent event) {
        Stage currentScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentScreen.close();
    }

    public static void closeNthWindow(int n) {
        Window usersListWindow = Stage.getWindows().get(n);
        ((Stage) usersListWindow).close();
    }

}
