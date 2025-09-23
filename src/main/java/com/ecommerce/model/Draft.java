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
    public static class DraftMemento{
        private final String draftId;
        private final String customerName;
        private final String phone;
        private final int productId;
        private DraftMemento(String draftId, String customerName, String phone, int productId) {
            this.draftId = draftId;
            this.customerName = customerName;
            this.phone = phone;
            this.productId = productId;
        }
    }
    public DraftMemento save(){
        return new DraftMemento(draftId,customerName,phone,productId);
    }
    public void restore(DraftMemento draftMemento){
        this.draftId = draftMemento.draftId;
        this.customerName = draftMemento.customerName;
        this.phone = draftMemento.phone;
        this.productId = draftMemento.productId;
    }
}
