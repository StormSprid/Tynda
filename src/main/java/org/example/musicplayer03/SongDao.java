package org.example.musicplayer03;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDao {

    // Метод для получения списка песен пользователя по его идентификатору
    public List<Song> getSongsByUserId(int userId) {
        List<Song> songs = new ArrayList<>();

        // Указать параметры подключения к базе данных
        String url = "jdbc:mysql://localhost/example";
        String username = "root";
        String password = "admin";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Создание объекта PreparedStatement для выполнения параметризованного SQL запроса
            String query = "SELECT * FROM Songs WHERE song_id IN (SELECT song_id FROM Playlist_Songs " +
                    "WHERE playlist_id IN (SELECT playlist_id FROM playlists WHERE user_id = ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            // Выполнение запроса
            ResultSet resultSet = preparedStatement.executeQuery();

            // Обработка результатов запроса
            while (resultSet.next()) {
                int songId = resultSet.getInt("song_id");
                String title = resultSet.getString("title");
                int artistId = resultSet.getInt("artist_id");
                String duration = resultSet.getString("duration");
                // Другие поля из таблицы Songs

                // Создание объекта Song и добавление в список
                Song song = new Song(songId, title, artistId, duration);
                songs.add(song);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

        return songs;
    }
}
