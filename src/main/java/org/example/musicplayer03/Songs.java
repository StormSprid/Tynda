package org.example.musicplayer03;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Songs {
    int SongId;
    String Name;
    String Artist;
    String genre;

    String urlMusic;
    String urlVocal;
    String urlPhoto;
    String urlLyrics;

    int counter;

    public Songs(int songId, String name, String artist, String genre, String urlMusic, String urlVocal, String urlPhoto, String urlLyrics) {
        SongId = songId;
        Name = name;
        Artist = artist;
        this.genre = genre;
        this.urlMusic = urlMusic;
        this.urlVocal = urlVocal;
        this.urlPhoto = urlPhoto;
        this.urlLyrics = urlLyrics;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getUrlMusic() {
        return urlMusic;
    }

    public void setUrlMusic(String urlMusic) {
        this.urlMusic = urlMusic;
    }

    public String getUrlVocal() {
        return urlVocal;
    }

    public void setUrlVocal(String urlVocal) {
        this.urlVocal = urlVocal;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlLyrics() {
        return urlLyrics;
    }

    public void setUrlLyrics(String urlLyrics) {
        this.urlLyrics = urlLyrics;
    }


    public int getSongId() {
        return SongId;
    }

    public void setSongId(int songId) {
        SongId = songId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void addCounter(int songId){
        Connection connection = null; // Здесь должен быть ваш способ подключения к базе данных
        PreparedStatement statement = null;
        try {
            // Открываем подключение к базе данных
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin");

            // Подготавливаем SQL запрос для обновления счетчика песни
            String sql = "UPDATE Songs SET counter = counter + 1 WHERE song_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, songId);

            // Выполнение SQL запроса
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Counter updated successfully for song ID: " + songId);
            } else {
                System.out.println("No rows updated, check if song ID: " + songId + " exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрытие ресурсов
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void updateKaraokeVisbility(VBox box, String vocal){
        if (vocal!=null){
            box.setVisible(true);
        }else {
            box.setVisible(false);
        }
    }
}
