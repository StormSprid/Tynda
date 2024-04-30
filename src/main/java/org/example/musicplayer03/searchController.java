package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.security.cert.PolicyNode;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class searchController {

    @FXML
    protected TextField searchField;
    @FXML
    private TableView<Songs> songTableView;

    @FXML
    private  VBox searchResultsContainer;

    public  void displaySearchResults(ResultSet resultSet) throws SQLException {
        String songName = resultSet.getString("title");
//        String artist = resultSet.getString("Artist");
        String photoUrl = resultSet.getString("UrlPhoto");

        // Создание графических элементов для каждой строки результата
        Text songNameText = new Text(songName);
//        Text artistText = new Text(artist);
        ImageView photoImageView = new ImageView(new Image(new File("src/main/resources" +photoUrl).toURI().toString()));

        // Создание куба (можете использовать другой контейнер или стилизацию)
        VBox songBox = new VBox();
        songBox.getChildren().addAll(songNameText, photoImageView);
        searchResultsContainer.getChildren().add(songBox);
    }
}
