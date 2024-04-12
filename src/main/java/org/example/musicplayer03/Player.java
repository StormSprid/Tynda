package org.example.musicplayer03;
import java.io.File;

public class Player extends MusicLib{



    public static void playSong(){

        playDouble("src/Music/My_Universe_music.wav","src/Music/My_Universe_vocals.wav");


    }
    public static void stopSong(){

        stopDouble();
    }

}
