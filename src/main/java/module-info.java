module com.example.compilerproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.compilerproject to javafx.fxml;
    exports com.example.compilerproject;
}