module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example to javafx.fxml;
    exports com.example;
    opens com.example.Models to javafx.base;
}
