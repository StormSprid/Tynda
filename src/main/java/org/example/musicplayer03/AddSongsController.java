package org.example.musicplayer03;

import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class AddSongsController extends Songs{
    @FXML  TextField songNameField;
    @FXML TextField ArtistNameIdField;
    @FXML TextField genreField;
    @FXML TextField urlPhotoField;
    @FXML TextField urlMusicField;
    @FXML TextField urlVocalField;
    @FXML TextField urlLyricsField;
    @FXML TextField durationField;

    public AddSongsController(int songId, String name, String artist, String genre, String urlMusic, String urlVocal, String urlPhoto, String urlLyrics) {
        super(songId, name, artist, genre, urlMusic, urlVocal, urlPhoto, urlLyrics);
    }


    @FXML
    private void confirmButtonSendToSqlServer(ActionEvent event){
        String songName = songNameField.getText();
        String artistId = ArtistNameIdField.getText();

        String genre = genreField.getText();
        String urlMusic = urlMusicField.getText();
        String urlVocal = urlVocalField.getText();
        String urlPhoto = urlPhotoField.getText();
        String urlLyric = urlLyricsField.getText();
        String duration = durationField.getText();

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda","root","admin")){
            String sql = "INSERT INTO songs (title,artist_id,urlMusic,urlVocal,genre,duration,urlPhoto,urlLyric,) VALUES (?,?,?,?,?,?,?,?)";
       try(PreparedStatement statement = connection.prepareStatement(sql)){
           statement.setString(1,songName);
           statement.setString(2,artistId);
           statement.setString(3,urlMusic);
           statement.setString(4,urlVocal);
           statement.setString(5,genre);
           statement.setString(6,duration);
           statement.setString(7,urlPhoto);
           statement.setString(8,urlLyric);
           int rowsInserted = statement.executeUpdate();
           if (rowsInserted > 0) {
               System.out.println("Запись успешно добавлена в базу данных.");
           }

       }
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Ошибка добавления");
        }
    }


}
