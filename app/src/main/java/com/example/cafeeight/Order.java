package com.example.cafeeight;

public class Order {
    private int orderId;
    private double totalAmount;
    private int totalItems;


    public Order(int orderId, double totalAmount, int totalItems) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }
}