package com.example.cafeeight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Fragment_Clickedorder extends AppCompatActivity {
    private ImageView imageView, plusBtn, minusBtn;
    private TextView textView, feeTxt, descriptionTxt, numberOrderTxt, priceTxt, addToCartBtn;

    private int numberOrder = 1;
    private double price = 100;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_order);

        initializeViews();

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String selectedName = intent.getStringExtra("name");
            int selectedImage = intent.getIntExtra("image", 0);
            double selectedPrice = intent.getDoubleExtra("price", 100);

            Log.d("Clicked Order", "Selected Name: " + selectedName);
            Log.d("Clicked Order", "Selected Image: " + selectedImage);

            if (selectedName != null && selectedImage != 0) {
                textView.setText(selectedName);
                imageView.setImageResource(selectedImage);
                price = selectedPrice;
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
        feeTxt = findViewById(R.id.feeTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        priceTxt = findViewById(R.id.feeTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        addToCartBtn = findViewById(R.id.addToCartBtn);
    }

    private void updatePrice() {
        double totalPrice = numberOrder * price;
        priceTxt.setText(String.valueOf(totalPrice));
    }

    private void addToCart() {
        String selectedName = textView.getText().toString();
        int quantity = numberOrder;
        double totalPrice = quantity * price;

        long id = databaseHelper.insertOrder(selectedName, quantity, totalPrice);

        if (id != -1) {
            showToast("Item added to cart");
        } else {
            showToast("Failed to add item to cart");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

