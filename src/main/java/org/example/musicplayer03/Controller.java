package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Controller {

    @FXML
    private Button playButton;
    @FXML
    private Button karaokeButton;

    @FXML
    private Button stopButton;
    @FXML
    private Label label;
    @FXML
    private Slider slider;
    private Timeline timeline;
    private int seconds;
    private boolean isPlaying = false;


    @FXML
    protected void play() {

//        MusicPlayer.playMusicWithTimer("src/Music/My_Universe_music.wav","src/Music/My_Universe_vocals.wav");
        Player.playSong();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int secs = seconds % 60;
            label.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        isPlaying = true;


    }


    @FXML
    protected void stop() {
        isPlaying = !isPlaying;
        if(!isPlaying) {



            Player.stopSong();
            timeline.stop();
            seconds = 0;
            label.setText("00:00:00");
        }
    }

    @FXML
    protected void Karaoke(){
        MusicLib.nonVoiceMod();
    }
//    protected void clearTimeLine(){
//        timeline.s
//    }
    @FXML
    protected void pause(){
        isPlaying = !isPlaying;
        if (!isPlaying) {

            MusicLib.pauseDouble();
            timeline.pause();

        }else {

            MusicLib.pauseDouble();
            timeline.play();
        }

    }




}



//commit

//            mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
//        slider.setValue(newValue.toSeconds());
//        lblDuration.setText(Double.toString((int)slider.getValue()));
//        } ));
//
//        mediaPlayer.setOnReady(()-> {
//Duration totalDuration = media.getDuration();
//                lblDuration.setText("00:00 / " +(int)media.getDuration().toSeconds() );
//
//        slider.setMax(totalDuration.toSeconds());
//
//        });
//Scene scene = mediaView.getScene();
//
//            mediaView.fitWidthProperty().bind(scene.widthProperty());
//        mediaView.fitHeightProperty().bind(scene.heightProperty());
//        //mediaPlayer.setAutoPlay(true);
//        }
//        }
//
//@FXML
//void sliderPressed(MouseEvent event) {
//    mediaPlayer.seek(Duration.seconds(slider.getValue()));
//}