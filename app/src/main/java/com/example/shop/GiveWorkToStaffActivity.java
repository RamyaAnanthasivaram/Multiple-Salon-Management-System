package com.example.shop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiveWorkToStaffActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout containerLayout;
    ImageView cartImageView, profileImageView, logoutImageView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;
    private DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_work_to_staff);

        // Change status bar color and battery icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.phoneColor));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        containerLayout = findViewById(R.id.containerLayout);
        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("STAFF"); // Remove title from action bar

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_nav, R.string.Close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        loadStaffData();

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiveWorkToStaffActivity.this, OwnerMenuActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiveWorkToStaffActivity.this, GiveWorkToStaffActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(GiveWorkToStaffActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    private void loadStaffData() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference staffRef = mDatabase.child("users").child(userId).child("staff");
            staffRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot staffSnapshot : dataSnapshot.getChildren()) {
                        String staffId = staffSnapshot.getKey();
                        String staffName = staffSnapshot.child("name").getValue(String.class);

                        View staffView = LayoutInflater.from(GiveWorkToStaffActivity.this).inflate(R.layout.staff_layout, null);
                        TextView staffNameTextView = staffView.findViewById(R.id.staffNameTextView);
                        staffNameTextView.setText(staffName);
                        staffView.setTag(staffId);
                        staffView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String selectedStaffId = (String) v.getTag();
                                String selectedStaffName = staffNameTextView.getText().toString();
                                Intent intent = new Intent(GiveWorkToStaffActivity.this, SendWorkToStaffActivity.class);
                                intent.putExtra("staffId", selectedStaffId);
                                intent.putExtra("staffName", selectedStaffName);
                                startActivity(intent);
                            }
                        });
                        containerLayout.addView(staffView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(GiveWorkToStaffActivity.this, "Failed to retrieve staff information.", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

