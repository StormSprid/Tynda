package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private Button playButton;
    @FXML
    private Button karaokeButton;

    @FXML
    private Button stopButton;

    // Метод, который будет вызываться при нажатии на кнопку Play
    @FXML
    protected void play() {
        // Ваш код для воспроизведения музыки
        MusicPlayer.playMusicWithTimer("src/Music/My_Universe_music.wav","src/Music/My_Universe_vocals.wav");
    }

    // Метод, который будет вызываться при нажатии на кнопку Stop
    @FXML
    protected void stop() {
        //код для остановки воспроизведения музыки
        MusicPlayer.stopMusicWithTimer();
    }

    @FXML
    protected void Karaoke(){
        MusicPlayer.karaoke();
    }
}
//commit