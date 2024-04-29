package org.example.musicplayer03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;



import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/musicplayer03/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());





        stage.setScene(scene);
        stage.setTitle("Tynda");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/logo.png")));

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
