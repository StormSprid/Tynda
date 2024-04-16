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
import javafx.animation.KeyFrame;
import javafx.util.Duration;


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
    private Button pauseButton;

    @FXML
    private Button startPlay;
    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private  Slider trackSlider;
    private Timeline timeline;
    private int totalSongDuration;
    private int seconds;


    @FXML
    private javafx.scene.control.ScrollPane HomePage;
    @FXML
    private ScrollPane topSongsPage;
    private Button currentActiveButton = null;




    private void updateButtonVisibility() {
        playButton.setVisible(!isPlaying);
        pauseButton.setVisible(isPlaying);

    }


    @FXML
    protected void play() {
        isPlaying = true;
        Player.playSong();
        updateButtonVisibility();
        timeline.play();
    }



    @FXML
    protected void stop() {
        isPlaying = !isPlaying;
        if (!isPlaying) {


            Player.stopSong();

        }


    }
    @FXML
    private void initialize() {
        // Инициализация интерфейса
        updateButtonVisibility();
        initializeSliders();
        initializeTimeline();
    }

    private void initializeSliders() {
        // Инициализация слайдеров
        trackSlider.setMin(0);
        trackSlider.setMax(100);
        trackSlider.setValue(0);

        // Обработчик изменения значения слайдера
        trackSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!trackSlider.isValueChanging()) {
                // Проверка, воспроизводится ли трек и не находится ли слайдер в процессе изменения
                if (isPlaying) {
                    MusicLib.setTrackPosition(newValue.doubleValue());
                }
            }
        });
    }

    private void initializeTimeline() {
        // Создание таймлайна для обновления слайдера каждую секунду
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (isPlaying && !trackSlider.isValueChanging()) {
                        int currentDuration = (int) MusicLib.getTrackPosition();
                        trackSlider.setValue((double) currentDuration);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }



    @FXML
    protected void pause() {
        isPlaying = !isPlaying;
        MusicLib.pauseDouble();
        updateButtonVisibility();
        timeline.play();

    }

    @FXML
    protected void setVolume(){
        MusicLib.setVolume(volumeSlider.getValue());
        double currentVolume = volumeSlider.getValue();
        volumeLabel.setText(String.format("%.0f%%",currentVolume));

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

