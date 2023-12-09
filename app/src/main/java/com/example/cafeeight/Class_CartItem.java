package com.example.cafeeight;

import java.util.Objects;

public class Class_CartItem {

    private String itemName;
    private int quantity;
    private double originalPrice; // Store the original price
    private double totalPrice; // Calculate total price based on quantity and original price

    public Class_CartItem(String itemName, int quantity, double originalPrice) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.totalPrice = calculateTotalPrice();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Class_CartItem cartItem = (Class_CartItem) obj;
        return itemName.equals(cartItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName);
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // Recalculate total price when quantity changes
        this.totalPrice = calculateTotalPrice();
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void resetPriceToOriginal() {
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        return quantity * originalPrice;
    }
}
