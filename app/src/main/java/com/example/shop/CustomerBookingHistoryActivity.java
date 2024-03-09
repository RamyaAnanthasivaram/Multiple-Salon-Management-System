package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerBookingHistoryActivity extends AppCompatActivity {

    private LinearLayout containerLayout;
    private DatabaseReference historyRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    ImageView cartImageView, profileImageView, logoutImageView;
    private String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking_history);

        containerLayout = findViewById(R.id.containerLayout);
        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24_black); // Set the custom drawable
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        customerId = currentUser.getUid();

        // Changed the reference to "history" under the customer's node
        historyRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId).child("history");
        retrieveDataFromFirebase();

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(CustomerBookingHistoryActivity.this, CustomerHomeActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(CustomerBookingHistoryActivity.this, CustomerCartActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(CustomerBookingHistoryActivity.this, CustomerBookingHistoryActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Navigate back when the back button on the toolbar is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveDataFromFirebase() {
        historyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Inflate item_staff_work.xml for each entry
                        View itemView = LayoutInflater.from(CustomerBookingHistoryActivity.this).inflate(R.layout.customerbookinghistoryrecycler, containerLayout, false);

                        // Find views in the inflated item layout
                        TextView customerNameTextView = itemView.findViewById(R.id.customerNameSV);
                        TextView customerPhoneTextView = itemView.findViewById(R.id.customerPhoneSV);
                        TextView preferenceTextView = itemView.findViewById(R.id.preferenceSV);
                        TextView dateTextView = itemView.findViewById(R.id.dateSV);
                        TextView timeTextView = itemView.findViewById(R.id.timeSV);
                        TextView serviceTextView = itemView.findViewById(R.id.serviceSV);

                        // Set data to TextViews
                        customerNameTextView.setText("Name: " + snapshot.child("customerName").getValue(String.class));
                        customerPhoneTextView.setText("Phone Number: " + snapshot.child("phoneNumber").getValue(String.class));
                        preferenceTextView.setText("Store Preference: " + snapshot.child("storePreference").getValue(String.class));
                        dateTextView.setText("Booking Date: " + snapshot.child("bookingDate").getValue(String.class));
                        timeTextView.setText("Selected Time: " + snapshot.child("selectedTime").getValue(String.class));
                        serviceTextView.setText("Service Name: " + snapshot.child("serviceName").getValue(String.class));

                        // Add the inflated item to the containerLayout
                        containerLayout.addView(itemView);
                    }
                } else {
                    // Handle case where no data exists
                    Toast.makeText(CustomerBookingHistoryActivity.this, "No booking history found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
                Toast.makeText(CustomerBookingHistoryActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
