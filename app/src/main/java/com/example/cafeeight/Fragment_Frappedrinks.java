package com.example.cafeeight;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment_Frappedrinks extends Fragment {

    private GridView Grid_FrappeDrinks;
    private String[] FrappeDrinks = {"Vanilla Bean Frappe", "Matcha Frappe", "Strawberry Frappe", "Blueberry Frappe"};
    private int[] imgFrappeDrinks = {R.drawable.product_vanillabeanfrappe, R.drawable.product_matchafrappe, R.drawable.product_strawberryfrappe, R.drawable.product_blueberryfrappe};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frappedrinks, container, false);

        // Grid View for Iced Coffee

        Grid_FrappeDrinks = view.findViewById(R.id.OrderGrid);
        CustomAdapter customAdapter = new CustomAdapter(FrappeDrinks, imgFrappeDrinks);
        Grid_FrappeDrinks.setAdapter(customAdapter);

        Grid_FrappeDrinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = FrappeDrinks[i];
                int selectedImage = imgFrappeDrinks[i];
                startActivity(new Intent(getActivity(), Fragment_Clickedorder.class)
                        .putExtra("name", selectedName)
                        .putExtra("image", selectedImage));
            }
        });

        setClickListener(view.findViewById(R.id.hotcoffee), new Fragment_Orders());
        setClickListener(view.findViewById(R.id.icedcoffee), new Fragment_IcedCoffee());
        setClickListener(view.findViewById(R.id.frappedrinks), new Fragment_Frappedrinks());
        setClickListener(view.findViewById(R.id.noncoffee), new Fragment_Noncoffee());
        setClickListener(view.findViewById(R.id.proteinshakes), new Fragment_Proteinshakes());

        // Set up the cart ImageView click listener
        ImageView cartImageView = view.findViewById(R.id.cart);
        cartImageView.setOnClickListener(v -> openCartFragment());

        return view;
    }

    // Navigate Through Nested Fragments
    private void setClickListener(View view, final Fragment fragment) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragment);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Use your container view's ID
        transaction.addToBackStack(null); // Optional: Add to the back stack
        transaction.commit();
    }

    // Method to open the CartFragment
    private void openCartFragment() {
        // Create an instance of the CartFragment
        Fragment_Cart cartFragment = new Fragment_Cart();

        // Get the FragmentManager
        assert getFragmentManager() != null;
        FragmentManager fragmentManager = getFragmentManager();

        // Start a FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new CartFragment
        fragmentTransaction.replace(R.id.fragment_container, cartFragment);

        // Add the transaction to the back stack
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    public class CustomAdapter extends BaseAdapter {
        private String[] imageNames;
        private int[] imagesPhoto;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagesPhoto) {
            this.imageNames = imageNames;
            this.imagesPhoto = imagesPhoto;
            this.layoutInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return imagesPhoto.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.row_frappedrinks, viewGroup, false);
            }
            TextView tvName = view.findViewById(R.id.nameFrappeDrinks);
            ImageView imageView = view.findViewById(R.id.imgFrappeDrinks);

            tvName.setText(imageNames[i]);
            imageView.setImageResource(imagesPhoto[i]);
            return view;
        }
    }
}
