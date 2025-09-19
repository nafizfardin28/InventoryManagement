package com.ecommerce.observer;

import com.ecommerce.model.Product;

public interface StockObserver {
    void onStockChanged(Product product);
}
