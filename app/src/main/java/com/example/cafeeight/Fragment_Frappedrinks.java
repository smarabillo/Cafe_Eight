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

public class Fragment_Frappedrinks extends Fragment {

    private GridView gridFrappeDrinks;
    private String[] nameFrappeDrinks = {"Vanilla Bean Frappe", "Matcha Frappe", "Strawberry Frappe", "Blueberry Frappe"};
    private int[] priceFrappeDrinks = {115, 155, 155, 155};
    private int[] imgFrappeDrinks = {R.drawable.product_vanillabeanfrappe, R.drawable.product_matchafrappe, R.drawable.product_strawberryfrappe, R.drawable.product_blueberryfrappe};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noncoffee, container, false);

        gridFrappeDrinks = view.findViewById(R.id.OrderGrid);
        CustomAdapter customAdapter = new CustomAdapter(nameFrappeDrinks, priceFrappeDrinks, imgFrappeDrinks);
        gridFrappeDrinks.setAdapter(customAdapter);

        gridFrappeDrinks.setOnItemClickListener((adapterView, item, position, id) -> {
            String selectedName = nameFrappeDrinks[position];
            int selectedPrice = priceFrappeDrinks[position];
            int selectedImage = imgFrappeDrinks[position];
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
                convertView = layoutInflater.inflate(R.layout.row_frappedrinks, parent, false);
            }
            TextView tvName = convertView.findViewById(R.id.nameFrappeDrinks);
            TextView tvPrice = convertView.findViewById(R.id.priceFrappeDrinks);
            ImageView imageView = convertView.findViewById(R.id.imgFrappeDrinks);

            tvName.setText(imageNames[position]);
            tvPrice.setText(String.valueOf(imagePrice[position]));
            imageView.setImageResource(imagesPhoto[position]);
            return convertView;
        }
    }
}

