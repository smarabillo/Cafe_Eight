package com.example.cafeeight;

import android.widget.ImageView;

import java.util.Objects;

class Fragment_CartItem {

    private String itemName;
    private int quantity;
    private double price;
    private double totalPrice;
    private double originalPrice;

    public Fragment_CartItem(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.originalPrice = originalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fragment_CartItem fragmentCartItem = (Fragment_CartItem) obj;
        return itemName.equals(fragmentCartItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName);
    }

    public double getTotalPrice() {
        return quantity + price - quantity;
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

    public void setPrice(double price){
        this.price = price;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
