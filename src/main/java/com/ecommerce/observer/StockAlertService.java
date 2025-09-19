package com.ecommerce.observer;

import com.ecommerce.model.Product;
import javafx.scene.control.Alert;

public class StockAlertService implements StockObserver{
    @Override
    public void onStockChanged(Product product) {
        if(product.getAvailablePieces()<5){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Low Stock Warning");
            alert.setHeaderText(null);
            alert.setContentText(product.getName() + " is running low ("+product.getAvailablePieces()+" left)");
            alert.show();
        }

    }
}
