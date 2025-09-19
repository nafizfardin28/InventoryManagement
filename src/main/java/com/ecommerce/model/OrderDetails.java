package com.ecommerce.model;

public class OrderDetails {
    private String orderId;

    private String customerName;
    private String phoneNumber;
    private String productId;
    private String productName;
    private int quantity;
    private double totalBill;
    private String orderDate;

    public OrderDetails(String orderId, String customerName, String phoneNumber,
                        String productId, String productName, int quantity, double totalBill, String orderDate) {
        this.orderId = orderId;

        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalBill = totalBill;
        this.orderDate = orderDate;
    }



    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalBill() { return totalBill; }
    public void setTotalBill(double totalBill) { this.totalBill = totalBill; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
