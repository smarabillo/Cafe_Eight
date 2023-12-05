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
    private TextView textView;
    private TextView numberOrderTxt;
    private TextView priceTxt;
    private TextView addToCartBtn;
    private double originalPrice;

    private int numberOrder = 1;

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

        // Use double for totalPrice since it's a calculated value
        double totalPrice = Double.parseDouble(priceTxt.getText().toString());

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
