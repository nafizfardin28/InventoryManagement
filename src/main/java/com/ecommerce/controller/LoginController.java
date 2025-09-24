package com.ecommerce.controller;
import com.ecommerce.session.Session;

import com.ecommerce.model.User;
import com.ecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = DataService.findUser(username, password);
        if (user != null) {

            Session.clear();
            Session.setCurrentUsername(username);
            Session.setCurrentRole(user.isAdmin() ? "admin" : "employee");

            Stage stage = (Stage) usernameField.getScene().getWindow();
            String fxml = user.isAdmin() ? "/com/ecommerce/control_panel.fxml" : "/com/ecommerce/employee_control_panel.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }
    @FXML
    private void resetButton() throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/password_reset.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,400);
        stage.setScene(scene);
    }
}