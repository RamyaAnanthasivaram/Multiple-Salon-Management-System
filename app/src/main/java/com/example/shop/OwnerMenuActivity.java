package com.example.shop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class OwnerMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView uploadImage, mStatus, addItem, addHome, addStaff;
    ImageView cartImageView, profileImageView, logoutImageView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);

        // Change status bar color and battery icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.phoneColor));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HOME");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_nav, R.string.Close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Find CardViews and ImageViews
        uploadImage = findViewById(R.id.upload_Img);
        mStatus = findViewById(R.id.status);
        addItem = findViewById(R.id.add_Store);
        addHome = findViewById(R.id.add_home);
        addStaff = findViewById(R.id.add_staff);
        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(OwnerMenuActivity.this, OwnerMenuActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(OwnerMenuActivity.this, OwnerAppointmentActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(OwnerMenuActivity.this, GiveWorkToStaffActivity.class));
            }
        });

        // Set onClick listeners for CardViews
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddItemActivity
                Intent uploadImageIntent = new Intent(OwnerMenuActivity.this, OwnerHomeActivity.class);
                startActivity(uploadImageIntent);
            }
        });

        mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatusActivity
                Intent statusIntent = new Intent(OwnerMenuActivity.this, OwnerAppointmentActivity.class);
                startActivity(statusIntent);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatusActivity
                Intent addItemIntent = new Intent(OwnerMenuActivity.this, AddItemActivity.class);
                startActivity(addItemIntent);
            }
        });

        addHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatusActivity
                Intent addItemIntent = new Intent(OwnerMenuActivity.this, AddHomeItemActivity.class);
                startActivity(addItemIntent);
            }
        });

        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatusActivity
                Intent addStaffIntent = new Intent(OwnerMenuActivity.this, GiveWorkToStaffActivity.class);
                startActivity(addStaffIntent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.owner_profile) {
            Intent intent = new Intent(this, OwnerHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.owner_appointment) {
            Intent intent = new Intent(this, OwnerAppointmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_staff) {
            Intent intent = new Intent(this, StaffRegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.owner_staff_schedule) {
            Intent intent = new Intent(this, GiveWorkToStaffActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_names) {
            Intent intent = new Intent(this, OwnerAddStaffNameActivity.class);
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
