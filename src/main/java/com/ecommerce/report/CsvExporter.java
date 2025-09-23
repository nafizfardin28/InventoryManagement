package com.ecommerce.report;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvExporter implements ReportExporter {
    @Override
    public void export(Report report, List<Order> orders,List<OrderDetails> customerOrders, String filePath) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filePath)) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.append("Name,Phone Number,ID,Total Orders\n");
                writer.append(report.getName()).append(",")
                .append(report.getPhoneNumber()).append(",")
                .append(report.getId()).append(",")
                        .append(String.valueOf(report.getTotalOrders())).append("\n");

                // Write headers
                writer.append("OrderID,ProductID,Quantity,Total,OrderDate\n");

                // Write order rows
                for (Order order : orders) {
                    writer.append(order.getOrderId()).append(",")
                            .append(order.getProductId()).append(",")
                            .append(String.valueOf(order.getQuantity())).append(",")
                            .append(String.valueOf(order.getTotalBill())).append(",")
                            .append(order.getOrderDate()).append("\n");
                }

                System.out.println("CSV file generated successfully at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}