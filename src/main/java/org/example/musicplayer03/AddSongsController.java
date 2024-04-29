package org.example.musicplayer03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSongsController extends Songs{


    public AddSongsController(int songId, String name, String artist, String genre, String urlMusic, String urlVocal, String urlPhoto, String urlLyrics) {
        super(songId, name, artist, genre, urlMusic, urlVocal, urlPhoto, urlLyrics);
    }

    public static void addSong(String name,String artist,String genre, String urlMusic, String urlVocal, String urlPhoto, String urlLyrics){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin")){
            String sqlInjection = "INSERT INTO songs (Name, Artist, Genre, UrlMusic, UrlVocal, UrlPhoto, UrlLyrics) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try(PreparedStatement statement = connection.prepareStatement(sqlInjection)){

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
