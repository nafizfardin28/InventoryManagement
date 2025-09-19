
package com.ecommerce;

import com.ecommerce.service.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {

        DatabaseService dbService = DatabaseService.getInstance();
        Connection conn = dbService.getConnection();
        System.out.println("Connection: " + conn);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/ecommerce/login.fxml"));
            Parent root = fxmlLoader.load();
            if (root == null) {
                throw new IOException("Failed to load FXML file");
            }
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("E-Commerce App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            Scene fallbackScene = createFallbackScene();
            primaryStage.setScene(fallbackScene);
            primaryStage.show();
        }
    }

    private Scene createFallbackScene() {
        javafx.scene.control.Label label = new javafx.scene.control.Label("Error loading application. Please check FXML resources.");
        return new Scene(label, 600, 400);
    }

    private void showDatabaseError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText("Failed to initialize database");
        alert.setContentText("Error: " + e.getMessage() + "\n\nThe application may not function correctly.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {

        System.out.println("Application stopping, closing database connections...");
        DatabaseService dbService = DatabaseService.getInstance();
        dbService.closeConnection();
    }
}