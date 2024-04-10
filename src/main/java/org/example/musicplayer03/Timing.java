package org.example.musicplayer03;


import java.util.Timer;
import java.util.TimerTask;

public class Timing extends MusicPlayer{
    private static Timer timer;
    public static void playMusicWithTimer(String filePathMusic, String filePathVocals) {
        MusicPlayer.play(filePathMusic, filePathVocals); // Начинаем воспроизведение музыки

        // Создаем новый таймер
        timer = new Timer();

        // Запускаем таймер, который каждую секунду увеличивает счетчик времени
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = 0;

            @Override
            public void run() {
                if(isSongPlaying) {
                    seconds++;
                    System.out.println(formatTime(seconds));// Выводим текущее время
                }
            }
        }, 0, 1000); // Начинаем сразу и повторяем каждую секунду
    }

    public static void stopMusicWithTimer() {
        MusicPlayer.stop(); // Останавливаем воспроизведение музыки
        isSongPlaying = false;
        // Останавливаем таймер
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}