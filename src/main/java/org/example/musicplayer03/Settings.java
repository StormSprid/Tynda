package org.example.musicplayer03;


import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Settings {


    static String[] badWords = {"сука", "Сука", "бля", "Бля", "ебанной", "ебанный", "ёбаных", "Жопа", "жопа"};

    public static void setTextOnTextArea(String currentLyrics, TextArea SongTextArea) {
        int currentSecond = MusicLib.getTrackPositionToInt();
        String dir = currentLyrics;
        boolean foundValidLines = false; // Флаг для отслеживания найденных корректных строк
        if (dir != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // Проверяем наличие нулевого байта в строке
                    if (line.startsWith("\uFEFF")) {
                        line = line.substring(1); // Удаляем нулевой байт из строки
                    }

                    String[] parts = line.split(";");
                    // Проверяем количество частей после разделения строки
                    if (parts.length == 2) {
                        foundValidLines = true; // Устанавливаем флаг в true, если найдена корректная строка

                        String time = parts[0];
                        String text = parts[1];


                        String[] timeParts = time.split(":");
                        int minute = Integer.parseInt(timeParts[0]);
                        int seconds = Integer.parseInt(timeParts[1]);


                        if (currentSecond == (minute * 60 + seconds)) {
                            for (String badWord : badWords) {
                                if (text.contains(badWord) || text.contains("$")) {
                                    MusicLib.nonVocalMod();
                                } else {
                                    MusicLib.vocalMod();
                                }
                            }
                            SongTextArea.appendText(text.replace("$", "") + "\n");

                        }
                    }


                }

                // Если не было найдено корректных строк, выводим соответствующее сообщение
                if (!foundValidLines || currentLyrics == null) {
                    SongTextArea.setText("Упс! Текст данной песни откроется на платной версии приложения!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SongTextArea.setText("Упс! Текст данной песни откроется на платной версии приложения!");
        }

    }
}