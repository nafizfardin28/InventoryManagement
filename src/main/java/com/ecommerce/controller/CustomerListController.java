package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.model.User;
import com.ecommerce.service.DataService;
import com.ecommerce.session.Session;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerListController {
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> idColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        customerTable.getItems().addAll( DataService.getCustomersFromOrders());
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) customerTable.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                Session.getCurrentRole().equalsIgnoreCase("admin") ? "/com/ecommerce/control_panel.fxml" : "/com/ecommerce/employee_control_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}