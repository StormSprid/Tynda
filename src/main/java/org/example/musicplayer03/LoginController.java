package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> DBUtils.logInUser(event, loginTextField.getText(), passwordTextField.getText()));
        registerButton.setOnAction(event -> DBUtils.changeScene(event, "register.fxml", null, -1));  // Передаем -1 как userId, так как он не нужен
    }
}
