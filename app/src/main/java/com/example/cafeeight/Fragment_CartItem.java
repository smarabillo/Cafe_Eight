package com.example.cafeeight;

import java.util.Objects;

class Fragment_CartItem {
    private String itemName;
    private int quantity;
    private double price;
    private int image;
    private double totalPrice;

    public Fragment_CartItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
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

    public int getItemImage() {
        return image;
    }

    public double getTotalPrice() {
        // Calculate the total price based on quantity and unit price
        return quantity * price;
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

    // You can remove this method if you don't use it elsewhere in your code
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
