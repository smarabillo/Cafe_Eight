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
        int imageViewResourceID = getDrawableResourceID(imageView);
        double totalPrice = Double.parseDouble(priceTxt.getText().toString());

        Fragment_CartItem fragmentCartItem = new Fragment_CartItem(selectedName, quantity, totalPrice);
        CartManager.getInstance().addToCart(fragmentCartItem);

        showToast("Item added to cart");
    }

    // Helper method to get the image resource ID from the ImageView
    private int getDrawableResourceID(ImageView imageView) {
        if (imageView.getTag() != null && imageView.getTag() instanceof Integer) {
            return (int) imageView.getTag();
        }
        return 0;  // Return a default value or handle it according to your needs
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static class CartManager {
        private static CartManager instance;
        private List<Fragment_CartItem> fragmentCartItems;

        private CartManager() {
            this.fragmentCartItems = new ArrayList<>();
        }

        public static CartManager getInstance() {
            if (instance == null) {
                instance = new CartManager();
            }
            return instance;
        }

        public void addToCart(Fragment_CartItem fragmentCartItem) {
            if (fragmentCartItem == null) {
                return; // Exit early if the item is null
            }

            if (fragmentCartItems == null) {
                // Initialize the cartItems list if it's null
                fragmentCartItems = new ArrayList<>();
            }

            int index = findItemIndex(fragmentCartItem);

            if (index != -1) {
                // Item already exists in the cart, update quantity and total price
                Fragment_CartItem existingItem = fragmentCartItems.get(index);
                existingItem.setQuantity(existingItem.getQuantity() + fragmentCartItem.getQuantity());

                // Adjust the total price calculation based on your requirements
                existingItem.setTotalPrice(existingItem.getTotalPrice() + fragmentCartItem.getTotalPrice());
            } else {
                // Item doesn't exist in the cart, add it
                fragmentCartItems.add(fragmentCartItem);
            }
        }

        private int findItemIndex(Fragment_CartItem newItem) {
            if (fragmentCartItems != null && newItem != null) {
                for (int i = 0; i < fragmentCartItems.size(); i++) {
                    Fragment_CartItem existingItem = fragmentCartItems.get(i);
                    if (existingItem.equals(newItem)) {
                        return i; // Item found, return its index
                    }
                }
            }
            return -1; // Item not found
        }

        public List<Fragment_CartItem> getCartItems() {
            return fragmentCartItems;
        }

    }
}
