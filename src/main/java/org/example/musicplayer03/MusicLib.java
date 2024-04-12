package org.example.musicplayer03;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.File;

import static javax.sound.sampled.FloatControl.*;


public class MusicLib  {

    private static Clip song;
    private static Clip musicClip;
    private static Clip vocalsClip;
    private static boolean playMusicOnly = false;
    private static boolean isPlaying = false;
    private static int songDuration;

    public static void playDouble(String musicPath, String vocalsPath){
        try{
            if(!isPlaying) {
                File musicFile = new File(musicPath);
                File vocalsFile = new File(vocalsPath);

                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(musicFile));

                vocalsClip = AudioSystem.getClip();
                vocalsClip.open(AudioSystem.getAudioInputStream(vocalsFile));

//            songDuration = getSongDuration(musicClip);


                musicClip.start();
                vocalsClip.start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        isPlaying = true;
    }

    public static void stopDouble(){
        isPlaying = false;
        musicClip.close();
        vocalsClip.close();

    }

    public static void pauseDouble() {
        if (musicClip != null && vocalsClip != null) {


            isPlaying = !isPlaying;
            if (!isPlaying) {
                musicClip.stop();
                vocalsClip.stop();
            } else {
                musicClip.start();
                vocalsClip.start();
            }
        }
    }
    public static void nonVoiceMod(){
        playMusicOnly = !playMusicOnly;
        FloatControl gainControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
        if(playMusicOnly){
            gainControl.setValue(-80f);
        }else{
            gainControl.setValue(0f);
        }
    }
    public static String getCurrentPositionMusic(){
        long currentPositionMicros = musicClip.getMicrosecondPosition();
        double currentPositionSeconds = currentPositionMicros / 1_000_000.0;


        int minutes = (int) currentPositionSeconds / 60;
        int seconds = (int) currentPositionSeconds % 60;


        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String getCurrentPositionVocals(){
        long currentPositionMicros = musicClip.getMicrosecondPosition();
        double currentPositionSeconds = currentPositionMicros / 1_000_000.0;


        int minutes = (int) currentPositionSeconds / 60;
        int seconds = (int) currentPositionSeconds % 60;


        return String.format("%02d:%02d", minutes, seconds);
    }

    private  static void setCurrenttiming(){

    }


}
