package com.example.cafeeight;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment_Orders extends Fragment {

    private GridView Grid_HotCoffee;
    private String[] HotCoffee = {"Brewed", "Latte", "Cappuccino"};
    private int[] imgHotCoffee = {R.drawable.prodcut_brewed, R.drawable.product_latte, R.drawable.product_cappuccino};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        // Grid View for Hot Coffee

        Grid_HotCoffee = view.findViewById(R.id.OrderGrid);
        CustomAdapter customAdapter = new CustomAdapter(HotCoffee, imgHotCoffee);
        Grid_HotCoffee.setAdapter(customAdapter);

        Grid_HotCoffee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = HotCoffee[i];
                int selectedImage = imgHotCoffee[i];
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

    private void openCartFragment() {
        // Create a new instance of the ShoppingCartFragment (replace this with your actual fragment class)
        Fragment Fragment_Cart = new Fragment_Cart();

        // Replace the current fragment with the shoppingCartFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, Fragment_Cart);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //    Navigate Through Nester Fragments
    private void setClickListener(View view, final Fragment fragment) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(fragment);
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        // Replace the current fragment with the new nested fragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Use your container view's ID
        transaction.addToBackStack(null); // Optional: Add to back stack
        transaction.commit();
    }

    //    On Click Products
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
                view = layoutInflater.inflate(R.layout.row_hotcoffee, viewGroup, false);
            }
            TextView tvName = view.findViewById(R.id.nameHotCoffee);
            ImageView imageView = view.findViewById(R.id.imgHotCoffee);

            tvName.setText(imageNames[i]);
            imageView.setImageResource(imagesPhoto[i]);
            return view;
        }
    }
}
