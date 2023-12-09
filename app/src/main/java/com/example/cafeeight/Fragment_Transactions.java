package com.example.cafeeight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_Transactions extends Fragment {

    private TextView textViewTotalAmount;
    private View view;  // Added: to store the inflated view

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Modified: Store the inflated view in the 'view' variable
        view = inflater.inflate(R.layout.fragment_transactions, container, false);

        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);

        // Ensure that databaseHelper is initialized
        if (Fragment_Cart.databaseHelper == null) {
            Fragment_Cart.databaseHelper = new DatabaseHelper(requireContext());
        }

        displayOrderDetails();

        return view;
    }

    private void displayOrderDetails() {
        // Check if 'view' is null before using it
        if (view == null) {
            return;
        }

        // Ensure that databaseHelper is initialized
        if (Fragment_Cart.databaseHelper == null) {
            Fragment_Cart.databaseHelper = new DatabaseHelper(requireContext());
        }

        List<Order> orderList = retrieveOrdersFromDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView emptyText = view.findViewById(R.id.emptyText);

        if (orderList != null && !orderList.isEmpty()) {
            // Display the details of all orders
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);

            // Create and set up the adapter with your orderList
            OrderAdapter orderAdapter = new OrderAdapter(orderList);
            recyclerView.setAdapter(orderAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        } else {
            // Display a message if there are no orders
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    private List<Order> retrieveOrdersFromDatabase() {
        return Fragment_Cart.databaseHelper.getAllConfirmedOrders();
    }
}
