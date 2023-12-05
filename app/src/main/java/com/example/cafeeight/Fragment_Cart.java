package com.example.cafeeight;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_Cart extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView itemsTotalTxt, totalPriceTxt, checkoutBtn;

    private static DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartmanager, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        itemsTotalTxt = view.findViewById(R.id.ItemTotal);
        totalPriceTxt = view.findViewById(R.id.totalPrice);
        checkoutBtn = view.findViewById(R.id.CheckOutBtn);

        List<Fragment_CartItem> fragmentCartItems = Fragment_Clickedorder.CartManager.getInstance().getCartItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(fragmentCartItems);
        recyclerView.setAdapter(cartAdapter);

        updateTotalAmount();

        return view;
    }

    private void updateTotalAmount() {
        double totalAmount = calculateTotalAmount();
        int totalItems = calculateTotalQuantity();

        itemsTotalTxt.setText("Total Items: " + totalItems);

        double totalPrice = totalAmount;
        totalPriceTxt.setText("Total Amount: ₱" + String.format("%.0f", totalPrice));

        checkoutBtn.setOnClickListener(v -> performCheckout(totalPrice, totalItems));

        if (cartAdapter != null) {
            List<Fragment_CartItem> updatedFragmentCartItems = Fragment_Clickedorder.CartManager.getInstance().getCartItems();
            Log.d("Fragment_Cart", "Updated Cart Items: " + updatedFragmentCartItems.size());
            cartAdapter.updateDataset(updatedFragmentCartItems);
        }
    }


    private void performCheckout(double totalAmount, int totalItems) {
        // Custom layout for order summary
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_transactions, null);
        TextView textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount);

        // Set order details in the custom layout
        String orderDetails = "Total Items " + "\t" + totalItems + "\n" + "Total Amount: ₱ " + totalAmount;
        textViewTotalAmount.setText(orderDetails);

        // Create AlertDialog with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Order Summary")
                .setView(view)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Handle Confirm button click
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle Cancel button click
                    dialog.dismiss();
                })
                .show();
    }

    private double calculateTotalAmount() {
        Fragment_Clickedorder.CartManager cartManager = Fragment_Clickedorder.CartManager.getInstance();

        List<Fragment_CartItem> fragmentCartItems = cartManager.getCartItems();

        double totalAmount = 0;

        if (fragmentCartItems != null) {
            for (Fragment_CartItem fragmentCartItem : fragmentCartItems) {
                totalAmount += fragmentCartItem.getTotalPrice();
            }
        }

        return totalAmount;
    }


    private int calculateTotalQuantity() {

        Fragment_Clickedorder.CartManager cartManager = Fragment_Clickedorder.CartManager.getInstance();
        List<Fragment_CartItem> fragmentCartItems = cartManager.getCartItems();
        int totalQuantity = 0;

        if (fragmentCartItems != null) {
            for (Fragment_CartItem fragmentCartItem : fragmentCartItems) {
                totalQuantity += fragmentCartItem.getQuantity();
            }
        }

        return totalQuantity;
    }

    // CartAdapter class
    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
        private List<Fragment_CartItem> fragmentCartItems;

        public CartAdapter(List<Fragment_CartItem> fragmentCartItems) {
            this.fragmentCartItems = fragmentCartItems;
        }

        public void updateDataset(List<Fragment_CartItem> newFragmentCartItems) {
            this.fragmentCartItems = newFragmentCartItems;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cartitem, parent, false);
            return new CartViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            Log.d("Fragment_Cart", "onBindViewHolder: position - " + position);

            Fragment_CartItem fragmentCartItem = fragmentCartItems.get(position);
            holder.bind(fragmentCartItem);
        }

        @Override
        public int getItemCount() {
            return fragmentCartItems != null ? fragmentCartItems.size() : 0;
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemViewImage;
        private TextView itemNameTxt, quantityTxt, priceTxt;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemViewImage = itemView.findViewById(R.id.itemViewImage);
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }

        public void bind(Fragment_CartItem fragmentCartItem) {
            itemNameTxt.setText(fragmentCartItem.getItemName());
            quantityTxt.setText("Quantity: " + fragmentCartItem.getQuantity());
            priceTxt.setText("Price: " + fragmentCartItem.getTotalPrice());

            int itemImageResourceId = fragmentCartItem.getItemImage();
            itemViewImage.setImageResource(itemImageResourceId);
        }
    }
}
