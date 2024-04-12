//package org.example.musicplayer03;
//
//import javax.sound.sampled.*;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.TimeUnit;
//
//
//public class MusicPlayer {
//
//    private static Clip clip;
//    public static Clip musicClip;
//    public static Clip vocalsClip;
//    public static boolean playMusicOnly = false;
//    public static  boolean isSongPlaying = false;
//    private static int songDuration;
//
//    public static void play(String filePathMusic, String filePathVocals) {
//        isSongPlaying =true;
//
//        try {
//            // Путь к файлу с музыкой
//            File musicFile = new File(filePathMusic);
//            File vocalsFile = new File(filePathVocals);
//
//            // Создание аудио потока из файла
//            musicClip = AudioSystem.getClip();
//            musicClip.open(AudioSystem.getAudioInputStream(musicFile));
//
//            vocalsClip = AudioSystem.getClip();
//            vocalsClip.open(AudioSystem.getAudioInputStream(vocalsFile));
//
//            songDuration = getSongDurationSeconds(musicClip);
//
//            // Воспроизведение музыки
//            musicClip.start();
//
//
//            // Воспроизведение вокала
//            vocalsClip.start();
//            isSongPlaying();
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void stop() {
//        if (isSongPlaying) {
//            isSongPlaying = false;
//            if (musicClip != null && musicClip.isRunning()) {
//                musicClip.stop();
//                musicClip.close();
//            }
//            if (vocalsClip != null && vocalsClip.isRunning()) {
//                vocalsClip.stop();
//                vocalsClip.close();
//            }
//        }
//    }
//
//    public static void karaoke() {
//        playMusicOnly = !playMusicOnly;
//        FloatControl gainControl = (FloatControl) vocalsClip.getControl(FloatControl.Type.MASTER_GAIN);
//        if (playMusicOnly) {
//            gainControl.setValue(-80.0f);
//        } else {
//            gainControl.setValue(0.0f);
//        }
//    }
//
//    public static String[] lyricsLineReader(String fileName) {
//        String[] defaultParts = {"No lyrics","no Timimg"};
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//
//            String line = br.readLine();
//            if (line != null) {
//                String[] parts = line.split("//");
//                return parts;
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return defaultParts;
//    }
//
//    public static void showLyricsOnTiming(String[] parts){
//        if(parts.length>1) {
//            String lyricsLine = parts[0];
//            String timing = parts[1];
//            long timingInMili = 100;
//
//            if (timing != null) {
//                String[] timeParts = timing.split(":");
//                int minutes = Integer.parseInt(timeParts[0]);
//                int seconds = Integer.parseInt(timeParts[1]);
//
//
//                timingInMili = TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
//            }
//
//            if (lyricsLine != null) {
//                System.out.println(lyricsLine);
//                try {
//                    Thread.sleep(timingInMili);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    private static Timer timer;
//    public static void playMusicWithTimer(String filePathMusic, String filePathVocals) {
//        play(filePathMusic, filePathVocals); // Начинаем воспроизведение музыки
//
//        // Создаем новый таймер
//        timer = new Timer();
//
//        // Запускаем таймер, который каждую секунду увеличивает счетчик времени
//        timer.scheduleAtFixedRate(new TimerTask() {
//            int seconds = 0;
//
//            @Override
//            public void run() {
//                if(seconds<=songDuration) {
//                    seconds++;
//                    System.out.println(formatTi                  me(seconds)); // Выводим текущее время
//                }else{
//                    stopMusicWithTimer();
//                }
//            }
//        }, 0, 1000); // Начинаем сразу и повторяем каждую секунду
//    }
//
//    public static void stopMusicWithTimer() {
//        MusicPlayer.stop(); // Останавливаем воспроизведение музыки
//
//        // Останавливаем таймер
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
//
//    private static String formatTime(int seconds) {
//        int minutes = seconds / 60;
//        seconds = seconds % 60;
//        return String.format("%02d:%02d", minutes, seconds);
//    }
//    public static void isSongPlaying(){
//        if(musicClip!=null ){
//            isSongPlaying = true;
//        }
//        else{
//            isSongPlaying = false;
//        }
//    }
//    private static int getSongDurationSeconds(Clip clip) {
//        long frames = clip.getFrameLength();
//        float frameRate = clip.getFormat().getFrameRate();
//        return (int) (frames / frameRate);
//    }
//}
//
