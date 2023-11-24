module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;


    opens com.uefs.librarymanager to javafx.fxml;
    exports com.uefs.librarymanager;
    exports com.uefs.librarymanager.dao;
    exports com.uefs.librarymanager.model;
    exports com.uefs.librarymanager.exceptions;
    exports com.uefs.librarymanager.utils;

    opens com.uefs.librarymanager.dao;
    opens com.uefs.librarymanager.exceptions;
    opens com.uefs.librarymanager.model;
    opens com.uefs.librarymanager.utils;
}