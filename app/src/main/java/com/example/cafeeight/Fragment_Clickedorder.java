package com.example.cafeeight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Clickedorder extends AppCompatActivity {

    // UI components
    private ImageView imageView, plusBtn, minusBtn;
    private TextView textView;
    private TextView numberOrderTxt;
    private TextView priceTxt;
    private TextView addToCartBtn;
    private TextView clearCartBtn;

    // Item details
    private double originalPrice;
    private int numberOrder = 1;

    // Cart manager instance
    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_order);

        // Initialize CartManager
        cartManager = new CartManager();

        // Initialize views
        initializeViews();

        // Get item details from the intent
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String selectedName = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image", 0);
            int selectedPrice = intent.getIntExtra("price", 0);

            originalPrice = selectedPrice;

            Log.d("Clicked Order", "Selected Name: " + selectedName);
            Log.d("Clicked Order", "Selected Image: " + selectedImage);

            if (selectedName != null && selectedImage != 0) {
                textView.setText(selectedName);
                imageView.setImageResource(selectedImage);
                priceTxt.setText(String.valueOf(selectedPrice));
                updatePrice();
            }
        }

        // Set click listeners for quantity adjustment buttons
        plusBtn.setOnClickListener(view -> {
            numberOrder++;
            numberOrderTxt.setText(String.valueOf(numberOrder));
            updatePrice();
        });

        minusBtn.setOnClickListener(view -> {
            if (numberOrder > 1) {
                numberOrder--;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                updatePrice();
            }
        });

        // Set click listener for add to cart button
        addToCartBtn.setOnClickListener(view -> addToCart());
    }

    // Initialize UI views
    private void initializeViews() {
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.tvname);
        priceTxt = findViewById(R.id.itemPrice);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        clearCartBtn = findViewById(R.id.clearCartBtn);
    }

    // Update total price based on quantity
    private void updatePrice() {
        if (numberOrder >= 0) {
            double totalPrice = numberOrder * originalPrice;
            priceTxt.setText(String.valueOf(totalPrice));
        } else {
            showToast("Quantity must be greater than or equal to 0");
        }
    }

    // Add the item to the cart
    // Add the item to the cart
    private void addToCart() {
        String selectedName = textView.getText().toString();
        int quantity = numberOrder;
        int totalPrice = (int) Double.parseDouble(priceTxt.getText().toString());

        // Retrieve the image resource ID using the ItemImageMapper
        int imageResourceId = Class_ItemImageMapper.getImageResourceIdForItem(selectedName);

        // Instantiate Class_CartItem with the actual data
        Class_CartItem fragmentCartItem = new Class_CartItem(selectedName, quantity, totalPrice, imageResourceId);

        // Add the item to the cart
        CartManager.getInstance().addToCart(fragmentCartItem);

        showToast("Item added to cart");
    }


    // Method to retrieve the image resource ID for a given item name
    private int getImageResourceIdForItem(String itemName) {
        // Implement the logic to retrieve the image resource ID based on the item name
        // For example, you might have a method or a data structure that maps item names to image resource IDs
        // Replace the following line with your actual implementation
        return R.drawable.ic_launcher_foreground; // Replace with the actual logic to get the image resource ID
    }

    // Show a Toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // CartManager class
    public static class CartManager {
        private static CartManager instance;
        private List<Class_CartItem> fragmentCartItems;

        private CartManager() {
            this.fragmentCartItems = new ArrayList<>();
        }

        // Singleton pattern to get a single instance of CartManager
        public static CartManager getInstance() {
            if (instance == null) {
                instance = new CartManager();
            }
            return instance;
        }

        // Add an item to the cart
        public void addToCart(Class_CartItem fragmentCartItem) {
            if (fragmentCartItem == null) {
                return;
            }

            if (fragmentCartItems == null) {
                fragmentCartItems = new ArrayList<>();
            }

            int index = findItemIndex(fragmentCartItem);

            if (index != -1) {
                // Item already exists in the cart, update quantity and total price
                Class_CartItem existingItem = fragmentCartItems.get(index);
                int newQuantity = existingItem.getQuantity() + fragmentCartItem.getQuantity();
                existingItem.setQuantity(newQuantity);
                existingItem.setTotalPrice(existingItem.getOriginalPrice() * newQuantity);
            } else {
                // Add a new item to the cart
                fragmentCartItem.setTotalPrice(fragmentCartItem.getOriginalPrice());
                fragmentCartItems.add(fragmentCartItem);
            }
        }

        // Find the index of an item in the cart
        private int findItemIndex(Class_CartItem newItem) {
            if (fragmentCartItems != null && newItem != null) {
                for (int i = 0; i < fragmentCartItems.size(); i++) {
                    Class_CartItem existingItem = fragmentCartItems.get(i);
                    if (existingItem.equals(newItem)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        // Get the list of items in the cart
        public List<Class_CartItem> getCartItems() {
            return fragmentCartItems;
        }

        // Clear the cart
        public void clearCart() {
            if (fragmentCartItems != null) {
                fragmentCartItems.clear();
            }
        }
    }
}
