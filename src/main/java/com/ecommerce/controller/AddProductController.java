package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductController {
    public TextField availablePiecesField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleAddProduct() {
        String id = idField.getText();
        String name = nameField.getText();
        String priceText = priceField.getText();
        String qtyText = availablePiecesField.getText();

        if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || qtyText.isEmpty()) {
            errorLabel.setText("All fields are required");
            return;
        }

        if (DataService.productExists(id, name)) {
            errorLabel.setText("Product ID or Name already exists!");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(qtyText);

            DataService.addProduct(new Product(id, name, price, quantity));
            errorLabel.setText("Product added successfully!");
            idField.clear();
            nameField.clear();
            priceField.clear();
            availablePiecesField.clear();

        } catch (NumberFormatException e) {
            errorLabel.setText("Price and quantity must be numeric.");
        } catch (Exception ex) {
            errorLabel.setText("Database error: " + ex.getMessage());
        }
    }


    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) idField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/control_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}