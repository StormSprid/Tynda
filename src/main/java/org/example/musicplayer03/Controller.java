package org.example.musicplayer03;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.util.Duration;



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
    private Button pauseButton;

    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider trackSlider;

    private Timeline timeline;



    @FXML
    private ScrollPane HomePage;
    @FXML
    private ScrollPane topSongsPage;
    private boolean isPlaying = false;
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
        if(MusicLib.isTrackDone()){
            updateButtonVisibility();
        }
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
        trackSlider.setOnMouseDragEntered(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }


        });
        trackSlider.setOnMouseClicked(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }

        });
        trackSlider.setOnDragDone(event -> {
            if (isPlaying) {
                MusicLib.setTrackPosition(trackSlider.getValue());
            }

        });
    }

    private void initializeTimeline() {
        // Создание таймлайна для обновления слайдера каждую секунду
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (isPlaying && !trackSlider.isValueChanging()) {

                        trackSlider.setValue((double) MusicLib.getTrackPosition());
//                        timerLabel.setText((String) MusicLib.getTrackPosition());
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
    protected void setVolume() {
        MusicLib.setVolume(volumeSlider.getValue());
        double currentVolume = volumeSlider.getValue();
        volumeLabel.setText(String.format("%.0f%%", currentVolume));

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


    
    @FXML
    private  void setTrackPosition(){
        MusicLib.setTrackPosition(trackSlider.getValue());
    }

}


