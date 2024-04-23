package org.example.musicplayer03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/musicplayer03/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load());





        stage.setScene(scene);
        stage.setTitle("Music Player");
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
