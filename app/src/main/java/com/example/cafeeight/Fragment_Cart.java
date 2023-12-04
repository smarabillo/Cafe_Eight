package com.example.cafeeight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView itemsTotalTxt, taxTxt, totalPriceTxt, checkoutBtn;

    private static DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartmanager, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        itemsTotalTxt = view.findViewById(R.id.ItemTotal);
        taxTxt = view.findViewById(R.id.TaxTxt);
        totalPriceTxt = view.findViewById(R.id.totalPrice);
        checkoutBtn = view.findViewById(R.id.CheckOutBtn);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(getCartItems());
        recyclerView.setAdapter(cartAdapter);

        updateTotalAmount();

        return view;
    }

    private List<CartItem> getCartItems() {
        return databaseHelper.getCartItems();
    }

    private void updateTotalAmount() {
        // Calculate total amount
        double totalAmount = calculateTotalAmount();

        // Update items total text
        itemsTotalTxt.setText("Items Total: ₱" + totalAmount);

        // Calculate tax (12% VAT)
        double taxAmount = 0.12 * totalAmount;
        taxTxt.setText("12% VAT: ₱" + String.format("%.2f", taxAmount));

        // Calculate total price including tax
        double totalPrice = totalAmount + taxAmount;
        totalPriceTxt.setText("Total: ₱" + String.format("%.2f", totalPrice));

        // Set up checkout button click listener
        checkoutBtn.setOnClickListener(v -> performCheckout(totalAmount, taxAmount, totalPrice));
    };

    private void performCheckout(double totalAmount, double taxAmount, double totalPrice) {
        String orderDetails = "Items Total: ₱" + totalAmount + "\n"
                + "Tax: ₱" + taxAmount + "\n"
                + "Total: ₱" + totalPrice;


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Order Summary")
                .setMessage(orderDetails)
                .setPositiveButton("Confirm", (dialog, which) -> {

                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


    private double calculateTotalAmount() {
        List<CartItem> cartItems = getCartItems();
        double totalAmount = 0;

        if (cartItems != null) {
            for (CartItem cartItem : cartItems) {
                totalAmount += cartItem.getTotalPrice();
            }
        }

        return totalAmount;
    }

    // CartItem class
    static class CartItem {
        private String itemName;
        private int quantity;
        private double price;

        public CartItem(String itemName, int quantity, double price) {
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        public double getTotalPrice() {
            return quantity * price;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }

    // CartAdapter class
    private static class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
        private final List<CartItem> cartItems;

        private List<CartItem> getCartItems() {
            return databaseHelper.getCartItems();
        }

        public CartAdapter(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cartitem, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            CartItem cartItem = cartItems.get(position);
            holder.bind(cartItem);
        }

        @Override
        public int getItemCount() {
            return cartItems != null ? cartItems.size() : 0;
        }
    }
    private static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTxt, quantityTxt, priceTxt;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }

        public void bind(CartItem cartItem) {
            itemNameTxt.setText(cartItem.getItemName());
            quantityTxt.setText("Quantity: " + cartItem.getQuantity());
            priceTxt.setText("Price: $" + cartItem.getPrice());
        }
    }
}
