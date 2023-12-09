package com.example.cafeeight;

import static com.example.cafeeight.Fragment_Cart.databaseHelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment_Transactions extends Fragment {

    private TextView textViewTotalAmount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);

        displayOrderDetails();

        return view;
    }

    private void displayOrderDetails() {
        Order latestOrder = retrieveLatestOrderFromDatabase();

        if (latestOrder != null) {
            String orderDetails = "Total Items: " + latestOrder.getTotalItems() +
                    "\nTotal Amount: ₱" + String.format("%.0f", latestOrder.getTotalAmount());
            textViewTotalAmount.setText(orderDetails);
        }
    }

    private Order retrieveLatestOrderFromDatabase() {
        return databaseHelper.getLatestOrder();
    }
}