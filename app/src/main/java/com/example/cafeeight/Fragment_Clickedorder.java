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

    private ImageView imageView, plusBtn, minusBtn;
    private TextView textView;
    private TextView numberOrderTxt;
    private TextView priceTxt;
    private TextView addToCartBtn;
    private TextView clearCartBtn;
    private double originalPrice;
    private int numberOrder = 1;

    private CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_order);

        // Initialize CartManager
        cartManager = new CartManager();

        initializeViews();


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

        addToCartBtn.setOnClickListener(view -> addToCart());
    }

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

    private void updatePrice() {
        if (numberOrder >= 0) {
            double totalPrice = numberOrder * originalPrice;
            priceTxt.setText(String.valueOf(totalPrice));
        } else {
            showToast("Quantity must be greater than or equal to 0");
        }
    }

    private void addToCart() {
        String selectedName = textView.getText().toString();
        int quantity = numberOrder;
        int totalPrice = (int) Double.parseDouble(priceTxt.getText().toString());
        Class_CartItem fragmentCartItem = new Class_CartItem(selectedName, quantity, totalPrice);
        CartManager.getInstance().addToCart(fragmentCartItem);

        showToast("Item added to cart");
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public static class CartManager {
        private static CartManager instance;
        private List<Class_CartItem> fragmentCartItems;

        private CartManager() {
            this.fragmentCartItems = new ArrayList<>();
        }

        public static CartManager getInstance() {
            if (instance == null) {
                instance = new CartManager();
            }
            return instance;
        }

        public void addToCart(Class_CartItem fragmentCartItem) {
            if (fragmentCartItem == null) {
                return;
            }

            if (fragmentCartItems == null) {
                fragmentCartItems = new ArrayList<>();
            }

            int index = findItemIndex(fragmentCartItem);

            if (index != -1) {
                Class_CartItem existingItem = fragmentCartItems.get(index);
                int newQuantity = existingItem.getQuantity() + fragmentCartItem.getQuantity();
                existingItem.setQuantity(newQuantity);
                existingItem.setPrice(existingItem.getPrice() * newQuantity); // Update price based on quantity
            } else {
                fragmentCartItem.setTotalPrice(fragmentCartItem.getPrice());
                fragmentCartItems.add(fragmentCartItem);
            }
        }


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

        public List<Class_CartItem> getCartItems() {
            return fragmentCartItems;
        }
    }
}