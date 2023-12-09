package com.example.cafeeight;

/**
 * Represents an order with details such as order ID, total amount, and total items.
 */
public class Order {

    private int orderId;
    private double totalAmount;
    private int totalItems;

    /**
     * Constructor to initialize an Order object.
     *
     * @param orderId     The unique identifier for the order.
     * @param totalAmount The total amount of the order.
     * @param totalItems  The total number of items in the order.
     */
    public Order(int orderId, double totalAmount, int totalItems) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    /**
     * Gets the order ID.
     *
     * @return The order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Gets the total amount of the order.
     *
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gets the total number of items in the order.
     *
     * @return The total number of items.
     */
    public int getTotalItems() {
        return totalItems;
    }
}
