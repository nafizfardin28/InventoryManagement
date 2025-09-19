package com.ecommerce.model;

public class Order {
    private String orderId;
    private int customerId;
    private String productId;
    private int Quantity;
    private double totalBill;
    private String orderDate;

    public Order(String orderId, int customerId, String productId, int Quantity, double totalBill, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.Quantity = Quantity;
        this.totalBill = totalBill;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return Quantity;
    }
    public double getTotalBill() {
        return totalBill;
    }
    public String getOrderDate() {
        return orderDate;
    }
}