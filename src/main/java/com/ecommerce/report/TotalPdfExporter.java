package com.ecommerce.report;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TotalPdfExporter implements ReportExporter{
    @Override
    public void export(Report report, List<Order> orders, List<OrderDetails> customerOrders, String filePath) throws IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        var font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        var fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Paragraph companyName = new Paragraph("IIT SuperShop")
                .setFont(font)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(companyName);
        Paragraph title = new Paragraph(report.getTitle())
                .setFont(font)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);
        Paragraph subtitle = new Paragraph(report.getSubTitle())
                .setFont(fontNormal)
                .setFontSize(22)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subtitle);
        Paragraph generationDate = new Paragraph("Generated on "+ LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                .setFont(fontNormal)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(generationDate);
        double SalesAmount = 0.0;
        int totalQuantity = 0;
        for (OrderDetails order : customerOrders) {
            SalesAmount += order.getTotalBill();
            totalQuantity += order.getQuantity();
        }
        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
        summaryTable.addCell(new Cell().add(new Paragraph("Total Sales Amount").setBold()));
        summaryTable.addCell(new Cell().add(new Paragraph(String.valueOf(SalesAmount))));
        summaryTable.addCell(new Cell().add(new Paragraph("Total Orders").setBold()));
        summaryTable.addCell(new Cell().add(new Paragraph(String.valueOf(customerOrders.size()))));
        summaryTable.addCell(new Cell().add(new Paragraph("Total Quantity Sold").setBold()));
        summaryTable.addCell(new Cell().add(new Paragraph(String.valueOf(totalQuantity))));
        summaryTable.setMarginBottom(20);
        document.add(summaryTable);
        Table ordersTable = new Table(UnitValue.createPercentArray(new float[]{2, 3, 3, 3, 4, 3, 2})).useAllAvailableWidth();
        String[] headers = {"Order ID", "Customer Name", "Phone Number", "Product ID", "Product Name", "Order Date","Total Price"};
        for (String h : headers) {
            ordersTable.addHeaderCell(new Cell().add(new Paragraph(h).setBold().setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(ColorConstants.GRAY));
        }
        for(OrderDetails order : customerOrders){
            ordersTable.addCell(order.getOrderId());
            ordersTable.addCell(order.getCustomerName());
            ordersTable.addCell(order.getPhoneNumber());
            ordersTable.addCell(order.getProductId());
            ordersTable.addCell(order.getProductName());
            ordersTable.addCell(order.getOrderDate());
            ordersTable.addCell(String.valueOf(order.getTotalBill()));
        }
        ordersTable.setMarginBottom(20);
        document.add(ordersTable);
        Paragraph footer = new Paragraph("Prepared By: ____________________\nThank you / Confidential")
                .setFont(fontNormal)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(30);
        document.add(footer);

        document.close();
    }
}
