package com.ecommerce.controller;

import com.ecommerce.command.BackupCommand;
import com.ecommerce.command.Command;
import com.ecommerce.command.RestoreCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlPanelController {
    @FXML
    private Button viewProductsButton;
    @FXML
    private Button viewCustomersButton;
    @FXML
    private Button viewOrdersButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button backButton;

    @FXML
    private void handleViewProducts() throws IOException {
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/product_list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    @FXML
    private void handleAddExistingProduct() throws IOException{
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    @FXML
    private void handleAddProduct() throws IOException {
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/add_product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

    @FXML
    private void handleViewCustomers() throws IOException {
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/customer_list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

    @FXML
    private void handleViewOrders() throws IOException {
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/order_list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) viewProductsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
    @FXML
    public void handleBackup() throws IOException {
        Command backup = new BackupCommand();
        backup.execute();
    }
    @FXML
    public void handleRestore() throws IOException {
        Command restore = new RestoreCommand();
        restore.execute();
    }

}