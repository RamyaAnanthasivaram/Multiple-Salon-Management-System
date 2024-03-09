package com.example.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CustomerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout containerLayout;


    ImageView cartImageView, profileImageView, logoutImageView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        // Change status bar color and battery icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.phoneColor));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        containerLayout = findViewById(R.id.containerLayout);
        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HOME"); // Remove title from action bar

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_nav, R.string.Close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Tinting the menu icon to black
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, android.R.color.black));

        // Call showUsersData directly
        showUsersData();

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(CustomerHomeActivity.this, CustomerHomeActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(CustomerHomeActivity.this, CustomerCartActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(CustomerHomeActivity.this, CustomerBookingHistoryActivity.class));
            }
        });
    }

    private void showUsersData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String storeName = userSnapshot.child("storeName").getValue(String.class);
                    String address = userSnapshot.child("address").getValue(String.class);
                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);
                    String userId = userSnapshot.getKey(); // Unique ID of the store owner

                    // Inflate the CardView layout
                    View cardViewLayout = getLayoutInflater().inflate(R.layout.cushome_layout, null);

                    // Find Views inside the CardView layout
                    ImageView userImageView = cardViewLayout.findViewById(R.id.shopImageView);
                    TextView storeNameTextView = cardViewLayout.findViewById(R.id.shopNameTextView);
                    TextView addressTextView = cardViewLayout.findViewById(R.id.shopAddressTextView);

                    // Load the image using Picasso
                    Picasso.get().load(imageUrl).into(userImageView);

                    // Set storeName and address to TextViews
                    storeNameTextView.setText(storeName);
                    addressTextView.setText(address);

                    cardViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Retrieve customer ID from SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            String customerId = sharedPreferences.getString("customerId", null);

                            // Navigate to ViewServiceActivity and pass userId as an extra
                            Intent intent = new Intent(CustomerHomeActivity.this, ViewServiceActivity.class);
                            intent.putExtra("customerId", customerId);
                            intent.putExtra("userId", userId);
                            intent.putExtra("storeName", storeName);
                            startActivity(intent);
                        }
                    });

                    // Add the inflated CardView to the container layout
                    containerLayout.addView(cardViewLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.staffUpdate) {
            Intent intent = new Intent(this, CustomerBookingHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, CustomerProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(this, CustomerCartActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
