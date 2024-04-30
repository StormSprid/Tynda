package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetupTopSongs {

    private Connection connection;

    public SetupTopSongs() {
        try {
            // Подключение к базе данных
            String url = "jdbc:mysql://localhost:3306/javafx-tynda"; // URL вашей базы данных
            String user = "root"; // Имя пользователя вашей базы данных
            String password = "admin"; // Пароль вашей базы данных
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<Songs> getTopSongs(int numberOfSongs) throws SQLException {
        List<Songs> topSongs = new ArrayList<>();
        String sql = "SELECT s.*, a.name AS artist " +
                "FROM Songs s " +
                "JOIN Artists a ON s.artist_id = a.artist_id " +
                "ORDER BY s.counter DESC LIMIT ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, numberOfSongs);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Songs song = new Songs(
                            resultSet.getInt("song_id"),
                            resultSet.getString("title"),
                            resultSet.getString("artist"),
                            resultSet.getString("genre"),
                            resultSet.getString("urlMusic"),
                            resultSet.getString("urlVocal"),
                            resultSet.getString("urlPhoto"),
                            resultSet.getString("urlLyric")
                    );
                    topSongs.add(song);
                }
            }
        }
        return topSongs;
    }

    // Метод для получения текущих песен из плейлиста по его идентификатору
    private List<Songs> getCurrentPlaylistSongs(int playlistId) throws SQLException {
        List<Songs> currentSongs = new ArrayList<>();
        String sql = "SELECT s.*, a.name AS artist " +
                "FROM Songs s " +
                "INNER JOIN Artists a ON s.artist_id = a.artist_id " +
                "INNER JOIN Playlist_Songs ps ON s.song_id = ps.song_id " +
                "WHERE ps.playlist_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Songs song = new Songs(
                            resultSet.getInt("song_id"),
                            resultSet.getString("title"),
                            resultSet.getString("artist"),
                            resultSet.getString("genre"),
                            resultSet.getString("urlMusic"),
                            resultSet.getString("urlVocal"),
                            resultSet.getString("urlPhoto"),
                            resultSet.getString("urlLyric")
                    );
                    currentSongs.add(song);
                }
            }
        }
        return currentSongs;
    }

    // Метод для удаления текущих песен из плейлиста по его идентификатору
    private void removeSongsFromPlaylist(int playlistId) throws SQLException {
        String sql = "DELETE FROM Playlist_Songs WHERE playlist_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playlistId);
            statement.executeUpdate();
        }
    }
    public void updateOrderNumbers(int playlistId) {
        try {
            List<Integer> songIds = getSongIdsByPopularity(playlistId);
            connection.setAutoCommit(false); // Выключаем авто-коммит для транзакции

            String updateSql = "UPDATE Playlist_Songs SET order_number = ? WHERE playlist_id = ? AND song_id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                for (int i = 0; i < songIds.size(); i++) {
                    updateStmt.setInt(1, i + 1); // Устанавливаем order_number начиная с 1
                    updateStmt.setInt(2, playlistId);
                    updateStmt.setInt(3, songIds.get(i));
                    updateStmt.executeUpdate();
                }
            }

            connection.commit(); // Подтверждаем изменения
            connection.setAutoCommit(true); // Возвращаем авто-коммит в исходное состояние
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getSongIdsByPopularity(int playlistId) throws SQLException {
        List<Integer> songIds = new ArrayList<>();
        String sql = "SELECT ps.song_id FROM Playlist_Songs ps JOIN Songs s ON ps.song_id = s.song_id WHERE ps.playlist_id = ? ORDER BY s.counter DESC LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, 10);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    songIds.add(resultSet.getInt("song_id"));
                }
            }
        }
        return songIds;
    }


    // Метод для вставки новых популярных песен в плейлист по его идентификатору
    private void insertSongsIntoPlaylist( List<Songs> songs, int playlistId) throws SQLException {
        String sql = "INSERT INTO Playlist_Songs (playlist_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Songs song : songs) {
                statement.setInt(1, playlistId);
                statement.setInt(2, song.getSongId());
                statement.addBatch();
            }
            statement.executeBatch(); // Сначала добавляем песни в плейлист
            updateOrderNumbers(playlistId);
        }
        }




    // Метод для замены песен в плейлисте на наиболее популярные песни
    public void replacePlaylistSongsWithTopSongs(int playlistId, int numberOfSongsToReplace) {
        try {
            // Получаем список наиболее популярных песен
            List<Songs> topSongs = getTopSongs(numberOfSongsToReplace);

            // Получаем текущие песни из плейлиста
            List<Songs> currentSongs = getCurrentPlaylistSongs(playlistId);

            // Удаляем текущие песни из плейлиста
            removeSongsFromPlaylist(playlistId);

            // Вставляем новые популярные песни в плейлист
            insertSongsIntoPlaylist(topSongs, playlistId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

