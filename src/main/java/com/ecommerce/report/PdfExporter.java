package com.ecommerce.report;

import com.ecommerce.model.Order;

import com.ecommerce.model.OrderDetails;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;


import java.time.LocalDateTime;
import java.util.List;

public class PdfExporter implements ReportExporter {
    @Override
    /*public void export(Report report,List<Order> orders, String filePath) {
        try {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(filePath);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph(report.getTitle())
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(33,66,99));
            document.add(title);
            document.add(new Paragraph(" "));

            // Customer info
            document.add(new Paragraph("Customer: " + report.getName()));
            document.add(new Paragraph("Phone Number: " + report.getPhoneNumber()));
            document.add(new Paragraph("Customer ID: " + report.getId()));
            document.add(new Paragraph("Total Orders: " + orders.size()));
            document.add(new Paragraph(" "));

            // Table with 5 columns
            Table table = new Table(new float[]{3, 2, 2, 2, 3});
            table.setWidth(100);

            // Headers
            String[] headers = {"Order ID", "Product ID", "Quantity", "Total", "Date"};
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            }

            // Rows
            for (Order order : orders) {
                table.addCell(new Cell().add(new Paragraph(order.getOrderId())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getProductId()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getQuantity()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getTotalBill()))));
                table.addCell(new Cell().add(new Paragraph(order.getOrderDate())));
            }

            document.add(table);
            document.close();

            System.out.println("PDF generated successfully at " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void export(Report report, List<Order> orders, List<OrderDetails> customerOrders, String filePath) {
        try {
            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(filePath);

            // Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document with margins
            Document document = new Document(pdf);
            document.setMargins(30, 20, 30, 20);

            // 🔹 Title
            Paragraph title = new Paragraph(report.getTitle())
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(33, 66, 99));
            document.add(title);
            document.add(new Paragraph("\n"));

            // 🔹 Customer Info Section (formatted nicely)
            document.add(new Paragraph("Customer: " + report.getName())
                    .setFontSize(12));
            document.add(new Paragraph("Phone Number: " + report.getPhoneNumber())
                    .setFontSize(12));
            document.add(new Paragraph("Customer ID: " + report.getId())
                    .setFontSize(12));
            document.add(new Paragraph("Total Orders: " + orders.size())
                    .setFontSize(12).setFontColor(new DeviceRgb(0, 102, 0)));
            document.add(new Paragraph("\n"));

            // 🔹 Order Table
            Table table = new Table(new float[]{4, 2, 2, 2, 4});
            table.setWidth(100);

            // Header row with background color
            String[] headers = {"Order ID", "Product ID", "Quantity", "Total", "Date"};
            for (String header : headers) {
                table.addHeaderCell(
                        new Cell()
                                .add(new Paragraph(header).setFontSize(11))
                                .setBackgroundColor(new DeviceRgb(200, 221, 242))
                                .setTextAlignment(TextAlignment.CENTER)
                );
            }

            // Alternate row coloring for readability
            boolean alternate = false;
            for (Order order : orders) {
                DeviceRgb bgColor = alternate ? new DeviceRgb(245, 245, 245) : (DeviceRgb) ColorConstants.WHITE;
                alternate = !alternate;

                table.addCell(new Cell().add(new Paragraph(order.getOrderId()))
                        .setBackgroundColor(bgColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getProductId())))
                        .setTextAlignment(TextAlignment.CENTER).setBackgroundColor(bgColor));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getQuantity())))
                        .setTextAlignment(TextAlignment.CENTER).setBackgroundColor(bgColor));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", order.getTotalBill())))
                        .setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(bgColor));
                table.addCell(new Cell().add(new Paragraph(order.getOrderDate()))
                        .setTextAlignment(TextAlignment.CENTER).setBackgroundColor(bgColor));
            }

            document.add(table);

            // 🔹 Footer / Generated Timestamp
            document.add(new Paragraph("\nGenerated on: " + LocalDateTime.now())
                    .setFontSize(10)
                    .setFontColor(new DeviceRgb(120, 120, 120))
                    .setTextAlignment(TextAlignment.RIGHT));

            document.close();

            System.out.println("✅ PDF generated successfully at " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
