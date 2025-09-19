package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.service.DataService;
import com.ecommerce.session.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderListController {
    @FXML
    private TableView<OrderDetails> orderTable;
    @FXML
    private TableColumn<OrderDetails, String> orderIdColumn;
    @FXML
    private TableColumn<OrderDetails, String> customerNameColumn;
    @FXML
    private TableColumn<OrderDetails, String> productIdColumn;
    @FXML
    private TableColumn<OrderDetails, String> QuantityColumn;
    @FXML
    private TableColumn<OrderDetails, String> totalBillColumn;
    @FXML
    private TableColumn<OrderDetails, String> orderDateColumn;
    @FXML
    private TableColumn<OrderDetails, String> phoneColumn;
    @FXML
    private TableColumn<OrderDetails, String> productNameColumn;

    @FXML
    private void initialize() {
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderId()));
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        productIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductId()));
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        totalBillColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalBill())));
        orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderDate()));
        QuantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        orderTable.getItems().addAll(DataService.getAllOrderDetails());
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) orderTable.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                Session.getCurrentRole().equalsIgnoreCase("admin") ? "/com/ecommerce/control_panel.fxml" : "/com/ecommerce/employee_control_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}