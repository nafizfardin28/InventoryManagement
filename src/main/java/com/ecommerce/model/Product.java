package com.ecommerce.model;

import com.ecommerce.observer.StockObserver;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private double price;
    private int availablePieces;
    private final List<StockObserver> observers=new ArrayList<>();
    public Product(String id, String name, double price,int availablePieces) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availablePieces = availablePieces;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    private void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.onStockChanged(this);
        }
    }
    public void setAvailablePieces(int availablePieces) {
        this.availablePieces = availablePieces;
        notifyObservers();
    }

    public int getAvailablePieces() {
        return availablePieces;
    }
    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }
}