package org.example.musicplayer03;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayer {
    private static Clip clip;
    public static Clip musicClip;
    public static Clip vocalsClip;
    public static boolean playMusicOnly = false;

    public static void play(String filePathMusic, String filePathVocals) {
        try {
            // Путь к файлу с музыкой
            File musicFile = new File(filePathMusic);
            File vocalsFile = new File(filePathVocals);

            // Создание аудио потока из файла
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(musicFile));

            vocalsClip = AudioSystem.getClip();
            vocalsClip.open(AudioSystem.getAudioInputStream(vocalsFile));

            // Воспроизведение музыки
            musicClip.start();

            // Установка уровня громкости для вокала
            FloatControl gainControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
            if (playMusicOnly) {
                gainControl.setValue(-80.0f);
            } else {
                gainControl.setValue(0.0f);
            }

            // Воспроизведение вокала
            vocalsClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void stop() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
        if (vocalsClip != null && vocalsClip.isRunning()) {
            vocalsClip.stop();
            vocalsClip.close();
        }
    }
    public static void karaoke() {
        playMusicOnly = !playMusicOnly;
        FloatControl gainControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
        if (playMusicOnly) {
            gainControl.setValue(-80.0f);
        } else {
            gainControl.setValue(0.0f);
        }
    }


}