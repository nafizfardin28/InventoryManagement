package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.observer.StockAlertService;
import com.ecommerce.service.DataService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> availableColumn;
    @FXML
    private TableColumn<Product, Void> actionColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        availableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAvailablePieces()).asObject());

        // Dummy data example, replace with actual database call
        productTable.getItems().addAll(DataService.getProducts());
        StockAlertService alertService = new StockAlertService();
        for (Product p : productTable.getItems()) {
            p.addObserver(alertService);
        }
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button addButton = new Button("+");
            private final Button subButton = new Button("-");
            private final HBox buttonBox = new HBox(5, subButton, addButton);

            {
                addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                subButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                addButton.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    product.setAvailablePieces(product.getAvailablePieces() + 1);
                    getTableView().refresh();
                    DataService.updateProductAvailablePieces(product.getId(), product.getAvailablePieces());
                });

                subButton.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product.getAvailablePieces() > 0) {
                        product.setAvailablePieces(product.getAvailablePieces() - 1);
                        getTableView().refresh();
                        DataService.updateProductAvailablePieces(product.getId(), product.getAvailablePieces());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }
    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) productTable.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/control_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}
