package org.example.musicplayer03;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class MySongs implements Initializable {

    @FXML
    private AnchorPane MySongsAnchor;

    @FXML
    private TilePane MySongsPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Здесь вы можете добавить логику инициализации, например, загрузку списка песен пользователя
        // и отображение их в MySongsPane
    }
}
