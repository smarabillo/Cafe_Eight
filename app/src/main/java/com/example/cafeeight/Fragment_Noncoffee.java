package com.example.cafeeight;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Noncoffee extends Fragment {

    private GridView gridNonCoffee;
    private String[] nonCoffeeNames = {"House Blend Iced Tea", "Matcha Latte", "Cucumber Lemonade", "Strawberry Soda"};
    private int[] nonCoffeePrices  = {55, 105, 85, 70};
    private int[] nonCoffeeImages = {R.drawable.product_houseblendicedtea, R.drawable.product_matchalatte, R.drawable.product_cucumberemonade, R.drawable.product_strawberrysoda};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noncoffee, container, false);

        gridNonCoffee = view.findViewById(R.id.OrderGrid);
        CustomAdapter customAdapter = new CustomAdapter(nonCoffeeNames, nonCoffeePrices, nonCoffeeImages);
        gridNonCoffee.setAdapter(customAdapter);

        gridNonCoffee.setOnItemClickListener((adapterView, item, position, id) -> {
            String selectedName = nonCoffeeNames[position];
            int selectedPrice = nonCoffeePrices[position];
            int selectedImage = nonCoffeeImages[position];
            startActivity(new Intent(requireActivity(), Fragment_Clickedorder.class)
                    .putExtra("name", selectedName)
                    .putExtra("price", selectedPrice)
                    .putExtra("image", selectedImage));
        });

        setClickListener(view.findViewById(R.id.hotcoffee), new Fragment_HotCoffee());
        setClickListener(view.findViewById(R.id.icedcoffee), new Fragment_IcedCoffee());
        setClickListener(view.findViewById(R.id.frappedrinks), new Fragment_Frappedrinks());
        setClickListener(view.findViewById(R.id.noncoffee), this);
        setClickListener(view.findViewById(R.id.proteinshakes), new Fragment_Proteinshakes());

        ImageView cartImageView = view.findViewById(R.id.cart);
        cartImageView.setOnClickListener(v -> openCartFragment());

        return view;
    }

    private void openCartFragment() {
        Fragment_Cart cartFragment = new Fragment_Cart();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, cartFragment)
                .addToBackStack(null)
                .commit();
    }

    // Navigate Through Nested Fragments
    private void setClickListener(View view, final Fragment fragment) {
        view.setOnClickListener(v -> replaceFragment(fragment));
    }

    private void replaceFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public class CustomAdapter extends BaseAdapter {
        private String[] imageNames;
        private int[] imagesPhoto;
        private int[] imagePrice;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagePrice, int[] imagesPhoto) {
            this.imageNames = imageNames;
            this.imagePrice = imagePrice;
            this.imagesPhoto = imagesPhoto;
            this.layoutInflater = LayoutInflater.from(requireActivity());
        }

        @Override
        public int getCount() {
            return imagesPhoto.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row_noncoffee, parent, false);
            }
            TextView tvName = convertView.findViewById(R.id.nameNonCoffee);
            TextView tvPrice = convertView.findViewById(R.id.priceNonCoffee);
            ImageView imageView = convertView.findViewById(R.id.imgNonCoffee);

            tvName.setText(imageNames[position]);
            tvPrice.setText(String.valueOf(imagePrice[position]));
            imageView.setImageResource(imagesPhoto[position]);
            return convertView;
        }
    }
}
