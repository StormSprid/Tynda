package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.animation.Timeline;

import javafx.scene.control.ScrollPane;

import javafx.scene.Parent;

import javafx.stage.Stage;


public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean isPlaying = false;
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

    private Label label;
    @FXML
    private Slider slider;
    private Timeline timeline;
    private int seconds;


    @FXML
    private javafx.scene.control.ScrollPane HomePage;
    @FXML
    private ScrollPane topSongsPage;
    private Button currentActiveButton = null;


    private void updateButtonVisibility() {
        playButton.setVisible(!isPlaying);
        stopButton.setVisible(isPlaying);
    }


    @FXML
    protected void play() {

        Player.playSong();

    }


    @FXML
    protected void stop() {
        isPlaying = !isPlaying;
        if (!isPlaying) {


            Player.stopSong();

        }


    }

    @FXML
    public void initialize() {
        updateButtonVisibility(); // Установка начального состояния кнопок
    }


    @FXML
    protected void pause() {
        isPlaying = !isPlaying;
        if (!isPlaying) {

            MusicLib.pauseDouble();
            timeline.pause();

        } else {

            MusicLib.pauseDouble();
            timeline.play();
        }
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

    private void PressButton(Button button) {
        if (currentActiveButton != null) {
            UnpressButton(currentActiveButton);
        }
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.57);");
        currentActiveButton = button;
    }

    private void UnpressButton(Button button) {
        button.setStyle("-fx-background-color: #ffdcbd;");
    }
}
