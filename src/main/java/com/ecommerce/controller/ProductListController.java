package com.ecommerce.controller;


import com.ecommerce.model.Draft;
import com.ecommerce.model.Product;
import com.ecommerce.observer.StockAlertService;
import com.ecommerce.service.DataService;
import com.ecommerce.session.Session;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


import java.io.IOException;

public class ProductListController {
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
    private TextField quantityField;

    @FXML private Button backButton;
    @FXML private Button buyButton;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField CustomerName;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private Button cartButton;
    @FXML private Pane draftsPane;
    @FXML private TableView<Draft> draftTable;
    @FXML private TableColumn<Draft, String> draftIdCol;
    @FXML private TableColumn<Draft, String> custNameCol;
    @FXML private TableColumn<Draft, String> phoneCol;
    @FXML private TableColumn<Draft, String> prodIdCol;
    @FXML private TableColumn<Draft, Integer> qtyCol;
    @FXML private TableColumn<Draft, Void> confirmCol;
    @FXML private TableColumn<Draft, Void> deleteCol;


    @FXML
    private void handleViewCart() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ecommerce/cart.fxml"));
        Stage stage = (Stage) cartButton.getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        availableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAvailablePieces()).asObject());
        productTable.getItems().addAll(DataService.getProducts());
        StockAlertService alertService = new StockAlertService();
        for (Product p : productTable.getItems()) {
            p.addObserver(alertService);
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleSaveDraft() {
        String customerName = CustomerName.getText();
        String phoneNumber  = PhoneNumber.getText();
        String productId    = productIdField.getText();
        String qtyText      = quantityField.getText();

        if (customerName.isEmpty() || phoneNumber.isEmpty() || productId.isEmpty() || qtyText.isEmpty()) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            showAlert("Error","Quantity must be a number");
            return;
        }

        boolean success = DataService.saveDraft(
                Session.currentUser, productId, quantity, customerName, phoneNumber);

        if (success) {
            showAlert("Info","Draft saved successfully!");
        } else {
            showAlert("Error","Draft could not be saved.");
        }
    }

    @FXML
    private void handleOrder() {
        String customerName = CustomerName.getText();
        String phoneNumber = PhoneNumber.getText();
        String productId = productIdField.getText();
        String quantityText = quantityField.getText();
        String username = Session.getCurrentUsername();
        if (username == null || username.isEmpty()) {
            showAlert("Error", "You must be logged in to place an order.");
            return;
        }

        if (productId.isEmpty() || quantityText.isEmpty() || customerName.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Error", "Please enter all Fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert("Error", "Quantity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid quantity format.");
            return;
        }

        for (Product product : productTable.getItems()) {
            if (product.getId().equals(productId)) {
                if (product.getAvailablePieces() < quantity) {
                    showAlert("Error", "Not enough stock available.");
                    return;
                }

                boolean success = DataService.processOrder(productId, quantity, customerName, phoneNumber);

                if (success) {
                    Session.setCurrentCustomerName(customerName);
                    product.setAvailablePieces(product.getAvailablePieces() - quantity);
                    showAlert("Success", "Order placed successfully.");
                    refreshProductTable();
                } else {
                    showAlert("Error", "Order failed.");
                }
                return;
            }
        }

        showAlert("Error", "Invalid Product ID.");
    }
    private void refreshProductTable() {
        productTable.getItems().setAll(DataService.getProducts());
    }
    @FXML
    private void handleViewDrafts() {
        draftsPane.setVisible(true);

        ObservableList<Draft> drafts = FXCollections.observableArrayList(DataService.getDrafts());
        draftTable.setItems(drafts);

        draftIdCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDraftId()));
        custNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerName()));
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));
        prodIdCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getProductId())));
        qtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        // Add Confirm button
        confirmCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Confirm");
            {
                btn.setOnAction(event -> {
                    Draft draft = getTableView().getItems().get(getIndex());
                    if (DataService.confirmDraft(draft.getDraftId())) {
                        showAlert("Success", "Draft converted to order");
                        refreshProductTable();
                        handleViewDrafts();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Delete");
            {
                btn.setOnAction(event -> {
                    Draft draft = getTableView().getItems().get(getIndex());
                    if (DataService.deleteDraft(draft.getDraftId())) {
                        showAlert("Success", "Draft deleted");
                        handleViewDrafts(); // reload table
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }


    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) productTable.getScene().getWindow();

        String role = Session.getCurrentRole();
        String fxmlPath;

        if ("employee".equalsIgnoreCase(role)) {
            fxmlPath = "/com/ecommerce/employee_control_panel.fxml";
        } else {
            fxmlPath = "/com/ecommerce/control_panel.fxml";
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

}