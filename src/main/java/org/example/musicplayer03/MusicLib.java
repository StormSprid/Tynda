package org.example.musicplayer03;
import javax.sound.sampled.*;

import java.io.File;




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

    public static void setVolume(double volume) {
        if (musicClip != null && vocalsClip != null) {
            FloatControl vocalControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
            FloatControl musicControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);

            float maxVolume = Math.min(vocalControl.getMaximum(), musicControl.getMaximum());
            float minVolume = Math.min(vocalControl.getMinimum(), musicControl.getMinimum());

            // Изменение дБ относительно минимального значения
            double dbChange = (volume / 100.0) * (6.0206 - (-80));
            // Масштабирование и добавление минимального значения
            float db = (float) (minVolume + dbChange);

            vocalControl.setValue(db);
            musicControl.setValue(db);
        }
    }

    private static double linearToLogarithmicVolume(double linearVolume) {
        double minDb = -80; // Минимальное значение в децибелах
        double maxDb = 6.0206; // Максимальное значение в децибелах (эквивалентно 100% громкости)

        // Преобразование линейного значения громкости в логарифмическое
        double logarithmicVolume = minDb + (maxDb - minDb) * linearVolume;

        // Убедимся, что результат не выходит за пределы допустимых значений
        return Math.min(maxDb, Math.max(minDb, logarithmicVolume));
    }



}
