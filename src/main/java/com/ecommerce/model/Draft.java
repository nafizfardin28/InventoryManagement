package com.ecommerce.model;

public class Draft {
    private String draftId;
    private String customerName;
    private String phone;
    private int productId;
    private int quantity;

    public Draft(String draftId, String customerName, String phone, int productId, int quantity) {
        this.draftId = draftId;
        this.customerName = customerName;
        this.phone = phone;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getDraftId() {
        return draftId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
