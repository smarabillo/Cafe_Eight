package com.example.cafeeight;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Activity_Main extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final Fragment_Dashboard fragmentDashboard = new Fragment_Dashboard();
    private final Fragment_Orders fragmentOrders = new Fragment_Orders();
    private final Fragment_Transactions fragmentTransaction = new Fragment_Transactions();
    private final Fragment_Profile fragmentProfile = new Fragment_Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------------------- Bottom Nav initialization --------------------------------

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentDashboard).commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.dashboard) {
                    loadFragment(fragmentDashboard);
                } else if (itemId == R.id.order) {
                    loadFragment(fragmentOrders);
                } else if (itemId == R.id.transaction) {
                    loadFragment(fragmentTransaction);
                } else if (itemId == R.id.profile) {
                    loadFragment(fragmentProfile);
                }
                return true;
            }
        });

// -------------------- DataBase ------------------------
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
