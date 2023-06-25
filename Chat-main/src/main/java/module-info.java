module com.example.borderpane {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.borderpane to javafx.fxml;
    exports com.example.borderpane;
}