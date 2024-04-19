package org.example.musicplayer03;
import java.io.File;
import java.util.ArrayList;


public class Player extends MusicLib {
    private static ArrayList<Songs> playlist = new ArrayList<>();
    public  int currentIndex = 0;



    public static void playPlaylist(){
        for(Songs song : playlist){
            System.out.println(song.getUrlMusic() + song.getUrlVocal());
//            playDouble(song.getUrlMusic(), song.getUrlVocal());
        }
    }


    public static ArrayList<Songs> playlistMySongs() {
        ArrayList<Songs> playlist = new ArrayList<>();

        Songs Lizer = new Songs(1, "Гори", "LIZER", "Russia", "src/Music/LIZER/music.wav", "src/Music/LIZER/vocals.wav", "src/main/resources/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        Songs Rihanna = new Songs(2, "Don't stop the music", "Rihanna", "Pop", "src/Music/Rihanna - Don't stop the music/music.wav", "src/Music/Rihanna - Don't stop the music/vocals.wav", "src/main/resources/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        Songs roses = new Songs(3, "Roses", "Imanbek", "Pop", "src/Music/roses/music.wav", "src/Music/roses/vocals.wav", "src/main/resources/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        Songs Strykalo = new Songs(3, "Kayen", "Strykalo", "Pop", "src/Music/Стрыкало/music.wav", "src/Music/Стрыкало/vocal.wav", "src/main/resources/icons/Лизер.jpg", "src/Lyrics/Гори.txt");
        playlist.add(Lizer);
        playlist.add(Rihanna);
        playlist.add(roses);
        playlist.add(Strykalo);
        return playlist;
    }

    public  Songs getCurrentTrack() {
        if (currentIndex >= 0 && currentIndex < playlist.size()) {
            return playlist.get(currentIndex);
        } else {
            return null;
        }
    }

}