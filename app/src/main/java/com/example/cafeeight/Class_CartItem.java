package com.example.cafeeight;

import java.util.Objects;

/**
 * Represents an item in the shopping cart.
 */
public class Class_CartItem {

    private String itemName;
    private int quantity;
    private final double originalPrice; // Store the original price
    private double totalPrice; // Calculate total price based on quantity and original price
    private int imageResourceId; // Add this field for the image resource ID

    /**
     * Constructor for Class_CartItem.
     *
     * @param itemName        The name of the item.
     * @param quantity        The quantity of the item.
     * @param originalPrice   The original price of the item.
     * @param imageResourceId The resource ID of the item image.
     */
    public Class_CartItem(String itemName, int quantity, double originalPrice, int imageResourceId) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.imageResourceId = imageResourceId;
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * Checks if two Class_CartItem objects are equal based on the item name.
     *
     * @param obj The object to compare.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Class_CartItem cartItem = (Class_CartItem) obj;
        return itemName.equals(cartItem.itemName);
    }

    /**
     * Generates a hash code based on the item name.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(itemName);
    }

    /**
     * Gets the name of the item.
     *
     * @return The item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the quantity of the item.
     *
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item and recalculates the total price.
     *
     * @param quantity The new quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // Recalculate total price when quantity changes
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * Gets the original price of the item.
     *
     * @return The original price.
     */
    public double getOriginalPrice() {
        return originalPrice;
    }

    /**
     * Gets the total price of the item.
     *
     * @return The total price.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the item.
     *
     * @param totalPrice The new total price.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Resets the total price to the original calculated value.
     */
    public void resetPriceToOriginal() {
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * Calculates the total price based on quantity and original price.
     *
     * @return The calculated total price.
     */
    private double calculateTotalPrice() {
        return quantity * originalPrice;
    }

    /**
     * Gets the resource ID of the item image.
     *
     * @return The resource ID.
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * Sets the resource ID of the item image.
     *
     * @param imageResourceId The new resource ID.
     */
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
