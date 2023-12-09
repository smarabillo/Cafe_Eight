package com.example.cafeeight;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
    private TextView itemsTotalTxt, totalPriceTxt, checkoutBtn, clearItems;
    public static DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartmanager, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        itemsTotalTxt = view.findViewById(R.id.ItemTotal);
        totalPriceTxt = view.findViewById(R.id.totalPrice);
        checkoutBtn = view.findViewById(R.id.CheckOutBtn);
        clearItems = view.findViewById(R.id.clearCartBtn);

        List<Class_CartItem> fragmentCartItems = Fragment_Clickedorder.CartManager.getInstance().getCartItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(fragmentCartItems);
        recyclerView.setAdapter(cartAdapter);

        databaseHelper = new DatabaseHelper(requireContext());

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
            List<Class_CartItem> updatedFragmentCartItems = Fragment_Clickedorder.CartManager.getInstance().getCartItems();
            Log.d("Fragment_Cart", "Updated Cart Items: " + updatedFragmentCartItems.size());
            cartAdapter.updateDataset(updatedFragmentCartItems);
        }
    }

    // Inside updateItemQuantity method
    private void updateItemQuantity(int position, int quantityDelta) {
        int currentQuantity = cartAdapter.fragmentCartItems.get(position).getQuantity();
        double originalPrice = cartAdapter.fragmentCartItems.get(position).getOriginalPrice();
        double newAmount;

        // Update the quantity based on the delta
        currentQuantity += quantityDelta;

        // Check if the new quantity is valid
        if (currentQuantity <= 0) {
            Toast.makeText(requireContext(), "Quantity cannot be negative.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate the new amount based on the change and original price
        newAmount = originalPrice * currentQuantity;

        // Create a new Fragment_CartItem object with updated information
        Class_CartItem updatedItem = new Class_CartItem(
                cartAdapter.fragmentCartItems.get(position).getItemName(),
                currentQuantity,
                newAmount
        );

        // Replace the existing item with the updated one
        cartAdapter.fragmentCartItems.set(position, updatedItem);

        // Notify the adapter about the change
        cartAdapter.notifyItemChanged(position);

        // Update total amount and items
        updateTotalAmount();
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
                    // Insert order data into the database
                    long orderId = saveOrderInDatabase(totalAmount, totalItems);

                    if (orderId != -1) {

                        Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show();
                        // You can also navigate to a success screen or perform other actions
                    } else {

                        Toast.makeText(requireContext(), "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                    }


                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private long saveOrderInDatabase(double totalAmount, int totalItems) {
        return DatabaseHelper.insertOrder(getContext(), totalAmount, totalItems);
    }


    private double calculateTotalAmount() {
        Fragment_Clickedorder.CartManager cartManager = Fragment_Clickedorder.CartManager.getInstance();

        List<Class_CartItem> fragmentCartItems = cartManager.getCartItems();

        double totalAmount = 0;

        if (fragmentCartItems != null) {
            for (Class_CartItem fragmentCartItem : fragmentCartItems) {
                totalAmount += fragmentCartItem.getTotalPrice();
            }
        }
        return totalAmount;
    }


    private int calculateTotalQuantity() {

        Fragment_Clickedorder.CartManager cartManager = Fragment_Clickedorder.CartManager.getInstance();
        List<Class_CartItem> fragmentCartItems = cartManager.getCartItems();
        int totalQuantity = 0;

        if (fragmentCartItems != null) {
            for (Class_CartItem fragmentCartItem : fragmentCartItems) {
                totalQuantity += fragmentCartItem.getQuantity();
            }
        }
        return totalQuantity;
    }


    // CartAdapter class
    public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
        private List<Class_CartItem> fragmentCartItems;

        public CartAdapter(List<Class_CartItem> fragmentCartItems) {
            this.fragmentCartItems = fragmentCartItems;
        }

        public void updateDataset(List<Class_CartItem> newFragmentCartItems) {
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
            // Full update without payload
            Class_CartItem fragmentCartItem = fragmentCartItems.get(position);
            holder.bind(fragmentCartItem);
        }


        @Override
        public int getItemCount() {
            return fragmentCartItems != null ? fragmentCartItems.size() : 0;
        }
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView minusQty, plusQty;;
        private TextView itemNameTxt, quantityTxt, priceTxt;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            minusQty = itemView.findViewById(R.id.minusQtyBtn);
            plusQty = itemView.findViewById(R.id.addQtyBtn);
        }

        public void bind(Class_CartItem fragmentCartItem) {
            itemNameTxt.setText(fragmentCartItem.getItemName());
            quantityTxt.setText("Quantity: " + fragmentCartItem.getQuantity());
            priceTxt.setText("Price: " + fragmentCartItem.getTotalPrice());
            minusQty.setOnClickListener(v -> updateItemQuantity(getAdapterPosition(), -1)); // Subtract quantity
            plusQty.setOnClickListener(v -> updateItemQuantity(getAdapterPosition(), 1)); // Add quantity
        }
    }
}
