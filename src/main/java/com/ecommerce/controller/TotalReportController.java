package com.ecommerce.controller;

import com.ecommerce.model.OrderDetails;
import com.ecommerce.report.*;
import com.ecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.security.cert.CertificateRevokedException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TotalReportController {
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private ChoiceBox<String> formatChoiceBox;
    @FXML
    private Label totalLabel;

    @FXML
    private void initialize() {
        formatChoiceBox.getItems().addAll("CSV", "PDF");
        formatChoiceBox.setValue("CSV");
    }
    @FXML
    private void handleTotalReport() throws Exception {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        String formatchoice =  formatChoiceBox.getValue();
        if(fromDate == null || toDate == null || formatchoice == null || formatchoice.isBlank()){
            Alert alert = new  Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
            return;
        }
        if(toDate.isBefore(fromDate) || toDate.isAfter(LocalDate.now())){
            Alert alert = new  Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select the dates carefully");
            alert.showAndWait();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDateTime = fromDate.atTime(LocalTime.MIN);
        String fromDateStamp = fromDateTime.format(formatter);
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
        String toDateStamp = toDateTime.format(formatter);
        System.out.println("From date: " + fromDateStamp);
        System.out.println("To date: " + toDateStamp);
        List<OrderDetails> orders = DataService.getAllOrderDetailsinARange(fromDateStamp,toDateStamp);
        double totalSale = 0.00;
        int quantitysold = 0;
        for (OrderDetails order : orders) {
            totalSale += order.getTotalBill();
            quantitysold += order.getQuantity();
        }
        ReportBuilder builder = new TotalReportBuilder();
        builder.setTitle("Total Sales Report");
        builder.setSubTitle("Date Range : "+fromDateStamp+" - "+toDateStamp);
        builder.setName(null);
        builder.setPhone(null);
        builder.setId(null);
        builder.setTotalOrders(orders.size());
        Report report = builder.buildReport();
        ReportExporter csvExporter = new TotalCsvExporter();
        ReportExporter pdfExporter = new TotalPdfExporter();
        if (formatchoice.equals("CSV")) {
            csvExporter.export(report,null,orders,"TotalReport.csv");
            totalLabel.setText("Total Sales Report Generated");
        }
        else{
            pdfExporter.export(report,null,orders,"TotalSales.pdf");
            totalLabel.setText("Total Sales Report Generated");
        }
    }
}
