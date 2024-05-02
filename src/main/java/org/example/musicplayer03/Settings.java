package org.example.musicplayer03;


import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Settings {



    static String[] badWords = {"сука", "Сука", "бля", "Бля", "ебанной", "ебанный", "ёбаных", "Жопа", "жопа","сук","Сук","Трахать","трахать"};

    @FXML
    RadioButton childModeActive;

    public static void setTextOnTextArea(String currentLyrics, TextArea SongTextArea,boolean isChildModeActive) {

        int currentSecond = MusicLib.getTrackPositionToInt();
        String dir = currentLyrics;
        boolean foundValidLines = false; // Флаг для отслеживания найденных корректных строк
        if (dir != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(dir, StandardCharsets.UTF_8))) {
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



    public static void loadArtists(ComboBox<String> comboBox) {
        try {
            // Установка соединения с базой данных
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin");
            Statement statement = connection.createStatement();

            // Выполнение запроса к базе данных для получения списка артистов
            ResultSet resultSet = statement.executeQuery("SELECT name FROM artists");

            // Добавление артистов в ComboBox
            while (resultSet.next()) {
                String artistName = resultSet.getString("name");
                comboBox.getItems().add(artistName);
            }

            // Закрытие ресурсов
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void loadText(String path) {
        if (path!= null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                StringBuilder content = new StringBuilder();
                String line;

                // Читаем файл построчно
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }


            } catch (IOException e) {
                // Обработка ошибок чтения файла
                System.err.println("Error reading file: " + e.getMessage());
            }
        }

    }
}

