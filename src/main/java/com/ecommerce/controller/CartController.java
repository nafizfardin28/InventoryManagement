package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.service.DataService;
import com.ecommerce.session.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CartController {

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> orderIdColumn;
    @FXML private TableColumn<Order, String> productNameColumn;
    @FXML private TableColumn<Order, Double> priceColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TableColumn<Order, String> totalPriceColumn;
    @FXML private TableColumn<Order, Void> removeColumn;
    @FXML private Label totalLabel;

    @FXML
    private void initialize() {
        String username = Session.getCurrentCustomerName();
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderId()));
        productNameColumn.setCellValueFactory(cellData -> {
            String productId = cellData.getValue().getProductId();
            Product product = DataService.getProductById(productId);
            return new SimpleStringProperty(product != null ? product.getName() : "Unknown");
        });
        priceColumn.setCellValueFactory(cellData -> {
            Product product = DataService.getProductById(cellData.getValue().getProductId());
            return new SimpleDoubleProperty(product != null ? product.getPrice() : 0.0).asObject();
        });
        quantityColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        totalPriceColumn.setCellValueFactory(cellData -> {
            Product product = DataService.getProductById(cellData.getValue().getProductId());
            int quantity = cellData.getValue().getQuantity();
            double total = (product != null ? product.getPrice() : 0.0) * quantity;
            return new SimpleDoubleProperty(total).asObject().asString();
        });

        removeColumn.setCellFactory(param -> new TableCell<Order,Void>() {

            private final Button removeBtn = new Button("Remove");
            {
                removeBtn.setOnAction(e -> {
                    Order order = getTableView().getItems().get(getIndex());
                    showRemoveDialog(order);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeBtn);
                }
            }
        });

        List<Order> orders = DataService.getOrdersByCustomer(username);
        orderTable.getItems().addAll(orders);

        recalculateTotal();
    }

    private void showRemoveDialog(Order order) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Remove Quantity");
        dialog.setHeaderText("Remove items from your cart");
        dialog.setContentText("Enter quantity to remove:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                int quantityToRemove = Integer.parseInt(input);
                if (quantityToRemove <= 0) {
                    showAlert("Quantity must be positive.");
                    return;
                }

                int orderQty = order.getQuantity();
                if (quantityToRemove > orderQty) {
                    showAlert("You can't remove more than you ordered.");
                    return;
                }

                boolean success = DataService.removeOrderQuantity(order, quantityToRemove);

                if (success) {
                    orderTable.getItems().clear();
                    List<Order> updatedOrders = DataService.getOrdersByCustomer(Session.getCurrentCustomerName());
                    orderTable.getItems().addAll(updatedOrders);
                    recalculateTotal();
                } else {
                    showAlert("Failed to remove quantity.");
                }

            } catch (NumberFormatException e) {
                showAlert("Invalid quantity.");
            }
        });
    }

    private void recalculateTotal() {
        double total = orderTable.getItems().stream()
                .mapToDouble(order -> {
                    Product product = DataService.getProductById(order.getProductId());
                    return (product != null) ? product.getPrice() * order.getQuantity() : 0.0;
                }).sum();

        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) orderTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ecommerce/product_list.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
