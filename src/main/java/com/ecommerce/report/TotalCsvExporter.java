package com.ecommerce.report;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TotalCsvExporter implements ReportExporter {
    @Override
    public void export(Report report, List<Order> orders,List<OrderDetails> customerOrders, String filePath) throws IOException {
        try (FileWriter csvWriter = new FileWriter(filePath)) {
            try (FileWriter writer = new FileWriter(filePath)) {


                // Write headers
                writer.append("OrderID,Customer Name,Phone Number,ProductID,Product Name,OrderDate,Total Price\n");

                // Write order rows
                for (OrderDetails order : customerOrders) {
                    writer.append(order.getOrderId()).append(",")
                            .append(order.getCustomerName()).append(",")
                            .append(String.valueOf(order.getPhoneNumber())).append(",")
                            .append(String.valueOf(order.getProductId())).append(",")
                            .append(String.valueOf(order.getProductName())).append(",")
                            .append(order.getOrderDate()).append(",")
                            .append(String.valueOf(order.getTotalBill()))
                            .append("\n");
                }

                System.out.println("CSV file generated successfully at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
