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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Fragment_HotCoffee extends Fragment {

    private GridView gridHotCoffee;
    private String[] hotCoffeeNames = {"Brewed", "Latte", "Cappuccino"};
    private int[] hotCoffeePrices = {55, 90, 90};
    private int[] hotCoffeeImages = {R.drawable.prodcut_brewed, R.drawable.product_latte, R.drawable.product_cappuccino};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotcoffee, container, false);

        gridHotCoffee = view.findViewById(R.id.OrderGrid);
        Fragment_HotCoffee.CustomAdapter customAdapter = new Fragment_HotCoffee.CustomAdapter(hotCoffeeNames, hotCoffeePrices, hotCoffeeImages);
        gridHotCoffee.setAdapter(customAdapter);

        gridHotCoffee.setOnItemClickListener((adapterView, item, position, id) -> {
            String selectedName = hotCoffeeNames [position];
            int selectedPrice = hotCoffeePrices[position];
            int selectedImage = hotCoffeeImages[position];
            startActivity(new Intent(requireActivity(), Fragment_Clickedorder.class)
                    .putExtra("name", selectedName)
                    .putExtra("price", selectedPrice)
                    .putExtra("image", selectedImage));
        });

        setClickListener(view.findViewById(R.id.hotcoffee), new Fragment_HotCoffee());
        setClickListener(view.findViewById(R.id.icedcoffee), new Fragment_IcedCoffee());
        setClickListener(view.findViewById(R.id.frappedrinks), new Fragment_Frappedrinks());
        setClickListener(view.findViewById(R.id.noncoffee), new Fragment_Noncoffee());
        setClickListener(view.findViewById(R.id.proteinshakes), new Fragment_Proteinshakes());

        ImageView cartImageView = view.findViewById(R.id.cart);
        cartImageView.setOnClickListener(v -> openCartFragment());

        return view;
    }

    private void setClickListener(View view, final Fragment fragment) {
        view.setOnClickListener(v -> replaceFragment(fragment));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openCartFragment() {
        Fragment_Cart cartFragment = new Fragment_Cart();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, cartFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private String[] imageNames;
        private int[] imagePrice;
        private int[] imagesPhoto;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[]imagePrice, int[] imagesPhoto) {
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
                convertView = layoutInflater.inflate(R.layout.row_hotcoffee, parent, false);
            }
            TextView tvName = convertView.findViewById(R.id.nameHotCoffee);
            TextView tvPrice = convertView.findViewById(R.id.priceHotCoffee);
            ImageView imageView = convertView.findViewById(R.id.imgHotCoffee);

            tvName.setText(imageNames[position]);
            tvPrice.setText(String.valueOf(imagePrice[position])); // Change this line
            imageView.setImageResource(imagesPhoto[position]);
            return convertView;
        }

    }
}

