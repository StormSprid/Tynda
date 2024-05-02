package org.example.musicplayer03;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {


    public static void changeScene(ActionEvent event, String fxmFile, String username, int userId){
        Parent root = null;
        if (username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmFile));
                root = loader.load();
                Controller Controller = loader.getController();
                Controller.setUserInformation(username, userId);

            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
           try {
               root = FXMLLoader.load(DBUtils.class.getResource(fxmFile));
           }catch (IOException e) {
               e.printStackTrace();
           }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psGetUserId = null;
        ResultSet resultSet = null;
        ResultSet rsUserId = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tynda", "root", "admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is already taken.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
                rsUserId = psInsert.getGeneratedKeys(); // Retrieve user_id of the new user

                if (rsUserId.next()) {
                    int userId = rsUserId.getInt(1); // Assuming the user_id is returned as the first column
                    changeScene(event, "main.fxml", username, userId); // Redirect to main after successful registration
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, psCheckUserExists, rsUserId, psInsert, connection);
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-tynda", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT user_id, password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        int userId = resultSet.getInt("user_id");
                        changeScene(event, "main.fxml", username, userId); // Pass userId to changeScene
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
    }

    public static void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResources(ResultSet rs1, PreparedStatement ps1, ResultSet rs2, PreparedStatement ps2, Connection conn) {
        closeResources(rs1, ps1, conn); // Close first set of resources
        closeResources(rs2, ps2, conn); // Close second set of resources, using the same connection
    }


}
