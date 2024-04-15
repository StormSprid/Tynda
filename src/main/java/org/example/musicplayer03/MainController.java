package org.example.musicplayer03;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label label_welcome;

    @FXML
    private Button button_logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "login.fxml", null);
            }
        });
    }

    public void setUserInformation(String username) {
        label_welcome.setText("Welcome " + username + "!");
    }
}

