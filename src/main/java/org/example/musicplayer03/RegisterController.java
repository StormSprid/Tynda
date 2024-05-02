package org.example.musicplayer03;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmTextField;  // Переименовано для согласованности
    @FXML
    private Button signInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnAction(event -> {
            if (!usernameTextField.getText().trim().isEmpty() && !passwordTextField.getText().trim().isEmpty() && passwordTextField.getText().equals(confirmTextField.getText())) {
                DBUtils.signUpUser(event, usernameTextField.getText(), passwordTextField.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information correctly to sign up.");
                alert.show();
            }
        });

        signInButton.setOnAction(event -> DBUtils.changeScene(event, "login.fxml", null, -1));  // Передаем -1 как userId, так как он не нужен
    }
}
