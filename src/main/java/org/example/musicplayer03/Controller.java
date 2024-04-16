package org.example.musicplayer03;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label label_welcome;

    @FXML
    private Button button_logout;

    @FXML
    private Button HomeBtn;

    @FXML
    private Button TopSongsBtn;

    @FXML
    private Button playButton;

    @FXML
    private Button karaokeButton;

    @FXML
    private Button stopButton;

    @FXML
    private ScrollPane HomePage;

    @FXML
    private ScrollPane topSongsPage;

    private boolean isPlaying = false;
    private Button currentActiveButton = null;

    private void updateButtonVisibility() {
        playButton.setVisible(!isPlaying);
        stopButton.setVisible(isPlaying);
    }

    // Метод, который будет вызываться при нажатии на кнопку Play
    @FXML
    protected void play() {
        // Ваш код для воспроизведения музыки
        MusicPlayer.play("src/Music/roses [music].wav","src/Music/roses [vocals].wav");
        isPlaying = true;
        updateButtonVisibility();
    }

    // Метод, который будет вызываться при нажатии на кнопку Stop
    @FXML
    protected void stop() {
        //код для остановки воспроизведения музыки
        MusicPlayer.stop();
        isPlaying = false;
        updateButtonVisibility();
    }

    @FXML
    protected void Karaoke(){
        MusicPlayer.karaoke();
    }

    @FXML
    private void showHome() {
        HomePage.setVisible(true);
        topSongsPage.setVisible(false);
        PressButton(HomeBtn);
        // добавить для других панелей
    }

    @FXML
    private void showTopSongs() {
        HomePage.setVisible(false);
        topSongsPage.setVisible(true);
        PressButton(TopSongsBtn);

        // добавить для других панелей
    }

    private void PressButton(Button button){
        if (currentActiveButton != null) {
            UnpressButton(currentActiveButton);
        }
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.57);");
        currentActiveButton = button;
    }

    private void UnpressButton(Button button) {
        button.setStyle("-fx-background-color: #ffdcbd;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", null);
            }
        });
        updateButtonVisibility(); // Установка начального состояния кнопок
    }

    public void setUserInformation(String username) {
        label_welcome.setText("Welcome " + username + "!");
    }
}
