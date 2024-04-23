package org.example.musicplayer03;
import javafx.application.Platform;

import javax.sound.sampled.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.Timer;



public class MusicLib {


    private static Clip song;
    private static Clip musicClip;
    private static Clip vocalsClip;
    private static boolean playMusicOnly = false;
    private static boolean isPlaying = false;
    private static int songDuration;
    private static Timer sliderTimer;
    private static final int SLIDER_UPDATE_INTERVAL = 100;

    public static void playDouble(String musicPath, String vocalsPath) {
        try {
            if (!isPlaying) {
                File musicFile = new File(musicPath);
                File vocalsFile = new File(vocalsPath);

                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(musicFile));

                vocalsClip = AudioSystem.getClip();
                vocalsClip.open(AudioSystem.getAudioInputStream(vocalsFile));


                musicClip.start();
                vocalsClip.start();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        isPlaying = true;
    }

    public static void stopDouble() {
        isPlaying = false;
        musicClip.close();
        vocalsClip.close();
        musicClip = null;
        vocalsClip = null;


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


    public static void setVolume(double volume) {
        if (musicClip != null && vocalsClip != null) {
            FloatControl vocalControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
            FloatControl musicControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);

            float minVolume = Math.min(vocalControl.getMinimum(), musicControl.getMinimum());

            // Изменение дБ относительно минимального значения
            double dbChange = (volume / 100.0) * (6.0206 - (-80));
            // Масштабирование и добавление минимального значения
            float db = (float) (minVolume + dbChange);

            vocalControl.setValue(db);
            musicControl.setValue(db);
        }
    }

    public static void setVocalVolume(double volume) {
        if (vocalsClip != null) {

            FloatControl vocalControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
            FloatControl musicControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);

            float minVolume = Math.min(vocalControl.getMinimum(), musicControl.getMinimum());
            double dbChange = (volume / 100.0) * (6.0206 - (-80));
            // Масштабирование и добавление минимального значения
            float db = (float) (minVolume + dbChange);

            vocalControl.setValue(db);
        }
    }


    public static void setTrackPosition(double position) {
        if (musicClip != null) {
            long clipPosition = (long) (position * musicClip.getMicrosecondLength() / 100);
            musicClip.setMicrosecondPosition(clipPosition);
            vocalsClip.setMicrosecondPosition(clipPosition);
        }
    }

    public static double getTrackPosition() {
        if (musicClip != null) {
            long clipPosition = musicClip.getMicrosecondPosition();
            long clipLength = musicClip.getMicrosecondLength();
            return (double) clipPosition / clipLength * 100;
        }
        return 0;
    }

    public static int getTrackPositionToInt() {
        if (musicClip != null) {
            long clipPosition = musicClip.getMicrosecondPosition();
            return (int) clipPosition / 1000000;
        }
        return 0;
    }

    public static int getTotalDuration() {

            return (int) musicClip.getMicrosecondLength() / 1000000;


    }


    public static boolean isTrackDone() {

        return getTrackPosition() >= getTotalDuration() || getTrackPosition() == 0;

    }

    public static boolean isSongLoaded() {
        return musicClip != null && vocalsClip != null;
    }

    public static String secondsToString(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;



        return String.format("%02d:%02d", minutes, remainingSeconds);
    }




}
