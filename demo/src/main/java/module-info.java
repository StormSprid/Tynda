module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}