package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class OwnerAppointmentActivity extends AppCompatActivity {

    private DatabaseReference userBookingsRef;
    private String userId;
    ImageView cartImageView, profileImageView, logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_appointment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Set the custom drawable
        }

        // Get current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(this, "User is not authenticated.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(OwnerAppointmentActivity.this, OwnerMenuActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(OwnerAppointmentActivity.this, OwnerAppointmentActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(OwnerAppointmentActivity.this, GiveWorkToStaffActivity.class));
            }
        });

        // Initialize Firebase database reference
        userBookingsRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("appointments");

        // Retrieve appointment details from Firebase and display in the layout
        userBookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout containerLayout = findViewById(R.id.owner_appointments_container);

                // Loop through each appointment
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Get appointment details
                    String customerId = appointmentSnapshot.child("customerId").getValue(String.class);
                    String serviceName = appointmentSnapshot.child("serviceName").getValue(String.class);
                    String customerName = appointmentSnapshot.child("customerName").getValue(String.class);
                    String phoneNumber = appointmentSnapshot.child("phoneNumber").getValue(String.class);
                    String bookingDate = appointmentSnapshot.child("bookingDate").getValue(String.class);
                    String selectedTime = appointmentSnapshot.child("selectedTime").getValue(String.class);
                    String storePreference = appointmentSnapshot.child("storePreference").getValue(String.class);

                    // Inflate the layout for each appointment
                    View appointmentView = LayoutInflater.from(OwnerAppointmentActivity.this).inflate(R.layout.appointment_item, containerLayout, false);

                    // Find TextViews inside the inflated layout
                    TextView customerIdTextView = appointmentView.findViewById(R.id.customerIdd);
                    TextView serviceNameTextView = appointmentView.findViewById(R.id.cartServiceName);
                    TextView customerNameTextView = appointmentView.findViewById(R.id.cartCustomerName);
                    TextView preferencesNameTextView = appointmentView.findViewById(R.id.preferences);
                    TextView phoneNumberTextView = appointmentView.findViewById(R.id.cartPhoneNumber);
                    TextView bookingDateTextView = appointmentView.findViewById(R.id.cartBookingDate);
                    TextView selectedTimeTextView = appointmentView.findViewById(R.id.cartSelectedTime);

                    // Set appointment details to TextViews
                    customerIdTextView.setText("Customer ID: " + customerId);
                    serviceNameTextView.setText("Service: " + serviceName);
                    customerNameTextView.setText("Customer: " + customerName);
                    phoneNumberTextView.setText("Phone: " + phoneNumber);
                    preferencesNameTextView.setText("Service: " + storePreference); // Display store preference
                    bookingDateTextView.setText("Date: " + bookingDate);
                    selectedTimeTextView.setText("Time: " + selectedTime);

                    // Add OnClickListener to the inflated layout
                    appointmentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Start CustomerStaffScheduleActivity and pass appointment details
                            Intent intent = new Intent(OwnerAppointmentActivity.this, CustomerStaffScheduleActivity.class);
                            intent.putExtra("customerId", customerId);
                            intent.putExtra("serviceName", serviceName);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("bookingDate", bookingDate);
                            intent.putExtra("selectedTime", selectedTime);
                            intent.putExtra("storePreference", storePreference);
                            startActivity(intent);
                        }
                    });

                    // Add the inflated layout to the container layout
                    containerLayout.addView(appointmentView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OwnerAppointments", "Error fetching appointments: " + databaseError.getMessage());
                Toast.makeText(OwnerAppointmentActivity.this, "Error fetching appointments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when the home button in the toolbar is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
