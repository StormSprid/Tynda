package org.example.musicplayer03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Module extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Module.class.getResource("/org/example/musicplayer03/ui.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        stage.setScene(scene);
        stage.setTitle("Music Player");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
