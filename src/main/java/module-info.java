/**
 *
 */
module com.example.projectwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.projectwork to javafx.fxml;
    exports com.example.projectwork;
}