module pt.ul.fc.css.democracia2.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jakarta.persistence;
    requires spring.core;
    requires spring.context;
    requires spring.beans;
    requires jakarta.transaction;
    requires spring.web;
    requires spring.data.jpa;

    opens pt.ul.fc.css.democracia2.javafx to javafx.fxml, javafx.web;
    opens pt.ul.fc.css.democracia2.javafx.presentation.control to javafx.fxml;
    exports pt.ul.fc.css.democracia2.javafx;
}