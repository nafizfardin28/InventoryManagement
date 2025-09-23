package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.report.*;
import com.ecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerReportController {

    @FXML
    private TextField CustomerName;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private ChoiceBox<String> formatchoicebox;
    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        formatchoicebox.getItems().addAll("CSV", "PDF");
        formatchoicebox.setValue("CSV");
    }
    @FXML
    private void handleCustomerReport() throws Exception {
        String customerName = CustomerName.getText();
        String phoneNumber = PhoneNumber.getText();
        String formatchoice = formatchoicebox.getValue();
        if(customerName.isEmpty() || phoneNumber.isEmpty()){
            errorLabel.setText("Please fill all the fields");
        }
        Customer customer = DataService.findCustomer(customerName,phoneNumber);
        if(customer != null){
            System.out.printf("Customer Name : %s , Phone Number : %s \n", customerName, phoneNumber);
        }
        else{
            errorLabel.setText("Customer Not Found");
        }
        List<Order> customerOrders = DataService.getOrdersByCustomerName(customerName, phoneNumber);
        ReportBuilder builder = new CustomerReportBuilder();
        builder.setTitle("Customer Order Report");
        builder.setName(customerName);
        builder.setPhone(phoneNumber);
        builder.setId(customer.getId());
        builder.setTotalOrders(customerOrders.size());
        Report report = builder.buildReport();
        ReportExporter csvExporter = new CsvExporter();
        ReportExporter pdfExporter = new PdfExporter();
        if(formatchoice.equals("CSV")){
            csvExporter.export(report,customerOrders,null,"CustomerReport.csv");
            errorLabel.setText("CSV Report Generated");
        }
        else{
            pdfExporter.export(report,customerOrders,null,"CustomerReport.pdf");
            errorLabel.setText("PDF Report Generated");
        }
    }
    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) CustomerName.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ecommerce/order_list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setScene(scene);
    }
}
