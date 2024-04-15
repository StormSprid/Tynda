module org.example.musicplayer04 {
    requires javafx.controls;
    requires javafx.fxml;


    requires java.desktop;
    requires java.sql;

    opens org.example.musicplayer03 to javafx.fxml;
    exports org.example.musicplayer03;
}