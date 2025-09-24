package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordResetController {

    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameStatus;
    @FXML
    private VBox usernamePane;
    @FXML
    private VBox passwordPane;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label passwordStatus;

    private String currentUsername; // store username after verification

    @FXML
    public void handleCheckUsername() throws SQLException {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            usernameStatus.setText("Please enter your username.");
            return;
        }

        User user = DataService.findUserbyName(username);

        if (user != null) {
            usernameStatus.setText("✅ User found");
            currentUsername = username;

            // Switch to password reset pane
            usernamePane.setVisible(false);
            usernamePane.setManaged(false);
            passwordPane.setVisible(true);
            passwordPane.setManaged(true);
        } else {
            usernameStatus.setText("❌ User not found");
        }
    }

    @FXML
    public void handlePasswordReset() throws SQLException {
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (newPass.isEmpty() || confirmPass.isEmpty()) {
            passwordStatus.setText("⚠ Please fill both password fields.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            passwordStatus.setText("❌ Passwords do not match.");
            return;
        }

        boolean success = DataService.updatePassword(currentUsername, newPass);
        if (success) {
            passwordStatus.setText("✅ Password reset successful.");
        } else {
            passwordStatus.setText("❌ Password reset failed.");
        }
    }
    @FXML
    public void handleBack() throws IOException {
        Stage stage = (Stage) newPasswordField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/control_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}
