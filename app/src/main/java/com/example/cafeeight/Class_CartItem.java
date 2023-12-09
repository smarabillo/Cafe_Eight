package com.example.cafeeight;

import java.util.Objects;

public class Class_CartItem {

    private String itemName;
    private int quantity;
    private double price;
    private int cartQuantity = 0; // Initialize with a default value
    private double originalPrice; // Declare with a default value of 0.0

    public Class_CartItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = price; // Store the initial price as originalPrice
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Class_CartItem fragmentCartItem = (Class_CartItem) obj;
        return itemName.equals(fragmentCartItem.itemName);
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
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = originalPrice;
    }

    public double getTotalPrice() {
        return originalPrice; // Calculate total price based on quantity and price
    }

    public void setTotalPrice(double totalPrice) {
        this.price = totalPrice; // Set the price based on the provided total price
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void resetPriceToOriginal() {
        this.price = originalPrice;
    }

    public double getOriginalPrice() {
        // Return the stored original price
        return originalPrice;
    }
}
