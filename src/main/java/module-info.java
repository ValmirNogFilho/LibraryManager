module com.uefs.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;
    opens com.uefs.librarymanager to javafx.fxml;
    exports com.uefs.librarymanager;
}