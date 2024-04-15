package org.example.musicplayer03;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController  implements Initializable {

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField ConfirmTextField;

    @FXML
    private Button signInButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!usernameTextField.getText().trim().isEmpty() && !passwordTextField.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event, usernameTextField.getText(), passwordTextField.getText());
                }else {
                    System.out.println("Please fill in all");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up");
                    alert.show();
                }
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", null);
            }
        });

    }
}
