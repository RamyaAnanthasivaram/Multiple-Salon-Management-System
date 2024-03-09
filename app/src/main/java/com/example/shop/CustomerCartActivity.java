package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

public class CustomerCartActivity extends AppCompatActivity implements PaymentResultListener {

    private DatabaseReference bookingsRef;
    private String customerId;
    private DataSnapshot currentBookingSnapshot;
    ImageView cartImageView, profileImageView, logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);

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

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(CustomerCartActivity.this, CustomerHomeActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(CustomerCartActivity.this, CustomerCartActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(CustomerCartActivity.this, CustomerBookingHistoryActivity.class));
            }
        });

        // Retrieve the current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            customerId = currentUser.getUid();
            Log.d("CustomerCartActivity", "UserId: " + customerId);
        } else {
            // Handle the case where the user is not logged in
            Log.e("CustomerCartActivity", "User is not logged in");
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();
            // You may want to redirect the user to the login screen or handle this case differently
            return;
        }

        // Initialize Firebase database reference
        bookingsRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId).child("bookings");

        // Retrieve booking details from Firebase and add to the layout
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout containerLayout = findViewById(R.id.containerLayout);
                containerLayout.removeAllViews(); // Clear previous views

                // Loop through each booking
                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    // Get booking details
                    String userId = bookingSnapshot.child("userId").getValue(String.class);
                    String serviceName = bookingSnapshot.child("serviceName").getValue(String.class);
                    String storePrice = bookingSnapshot.child("storePrice").getValue(String.class);
                    String storePreference = bookingSnapshot.child("storePreference").getValue(String.class);
                    String storeName = bookingSnapshot.child("whichStore").getValue(String.class);
                    String customerName = bookingSnapshot.child("customerName").getValue(String.class);
                    String phoneNumber = bookingSnapshot.child("phoneNumber").getValue(String.class);
                    String bookingDate = bookingSnapshot.child("bookingDate").getValue(String.class);
                    String selectedTime = bookingSnapshot.child("selectedTime").getValue(String.class);
                    String status = bookingSnapshot.child("status").getValue(String.class);
                    String customerId = bookingSnapshot.child("customerId").getValue(String.class); // Retrieve customerId

                    // Inflate the card view layout for each booking
                    View cardViewLayout = getLayoutInflater().inflate(R.layout.cartview, null);

                    // Find Views inside the CardView layout
                    TextView salonsIdTextView = cardViewLayout.findViewById(R.id.salonUserid);
                    TextView serviceNameTextView = cardViewLayout.findViewById(R.id.cartServiceName);
                    TextView storePriceTextView = cardViewLayout.findViewById(R.id.cartStorePrice);
                    TextView storePreferenceTextView = cardViewLayout.findViewById(R.id.cartSalonPre);
                    TextView storeNameTextView = cardViewLayout.findViewById(R.id.cartstoreName);
                    TextView customerNameTextView = cardViewLayout.findViewById(R.id.cartCusName);
                    TextView phoneNumberTextView = cardViewLayout.findViewById(R.id.cartCusPhone);
                    TextView bookingDateTextView = cardViewLayout.findViewById(R.id.cartDate);
                    TextView selectedTimeTextView = cardViewLayout.findViewById(R.id.cartTime);
                    TextView cusId = cardViewLayout.findViewById(R.id.custId);

                    // Set booking details to TextViews
                    cusId.setText("Customer Id: " + customerId);
                    salonsIdTextView.setText("ID:" + userId);
                    serviceNameTextView.setText("Service Booked:" + serviceName);
                    storePriceTextView.setText("Price:" + storePrice);
                    storePreferenceTextView.setText("Preference:" + storePreference);
                    storeNameTextView.setText("Salon Name:" + storeName);
                    customerNameTextView.setText("Customer Name:" + customerName);
                    phoneNumberTextView.setText("Phone Number:" + phoneNumber);
                    bookingDateTextView.setText("Date of Booking:" + bookingDate);
                    selectedTimeTextView.setText("Time:" + selectedTime);

                    // Set onClickListener for the card to initiate Razorpay payment process
                    cardViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentBookingSnapshot = bookingSnapshot;
                            startPayment(); // Pass the booking snapshot to start payment process
                        }
                    });

                    // Add the inflated CardView layout to the container layout
                    containerLayout.addView(cardViewLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CustomerCartActivity", "Error fetching bookings: " + databaseError.getMessage());
                Toast.makeText(CustomerCartActivity.this, "Error fetching bookings", Toast.LENGTH_SHORT).show();
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

    // Method to initiate Razorpay payment process
    private void startPayment() {
        // Initialize Razorpay Checkout
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_U2XWpODmhRkL0l"); // Replace with your Razorpay key ID

        try {
            // Create payment options
            JSONObject options = new JSONObject();
            options.put("name", "Shop Name");
            options.put("description", "Payment for Booking");
            options.put("currency", "INR");
            options.put("amount", "10000"); // Replace with the actual amount in paise
            options.put("prefill.email", "customer@example.com");
            options.put("prefill.contact", "9999999999");

            // Open Razorpay Checkout activity
            checkout.open(CustomerCartActivity.this, options);
        } catch (Exception e) {
            Log.e("CustomerCartActivity", "Error in starting Razorpay Checkout", e);
            Toast.makeText(this, "Error in payment", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle payment success
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // Get the userId from the bookingSnapshot
        String userId = currentBookingSnapshot.child("userId").getValue(String.class);

        // Save booking details to Firebase under the correct userId
        saveDetailsUnderUserId(currentBookingSnapshot, userId, customerId);
    }

    // Handle payment error
    @Override
    public void onPaymentError(int code, String response) {
        Log.d("PaymentError", "Payment failed: " + response);
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
    }

    // Method to save booking details under userId
    private void saveDetailsUnderUserId(DataSnapshot bookingSnapshot, String userId, String customerId) {
        // Save booking details to Firebase under the user's ID
        DatabaseReference userBookingsRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("appointments");

        // Get all booking details
        String serviceName = bookingSnapshot.child("serviceName").getValue(String.class);
        String storePrice = bookingSnapshot.child("storePrice").getValue(String.class);
        String storePreference = bookingSnapshot.child("storePreference").getValue(String.class);
        String storeName = bookingSnapshot.child("whichStore").getValue(String.class);
        String customerName = bookingSnapshot.child("customerName").getValue(String.class);
        String phoneNumber = bookingSnapshot.child("phoneNumber").getValue(String.class);
        String bookingDate = bookingSnapshot.child("bookingDate").getValue(String.class);
        String selectedTime = bookingSnapshot.child("selectedTime").getValue(String.class);
        String status = bookingSnapshot.child("status").getValue(String.class);
        String cusId = bookingSnapshot.child("cusId").getValue(String.class); // Retrieve cusId

        // Create a HashMap to hold all booking details including customerId
        HashMap<String, Object> bookingDetails = new HashMap<>();
        bookingDetails.put("customerId", customerId); // Add customer ID
        bookingDetails.put("cusId", cusId); // Add cusId
        bookingDetails.put("serviceName", serviceName);
        bookingDetails.put("storePrice", storePrice);
        bookingDetails.put("storePreference", storePreference);
        bookingDetails.put("storeName", storeName);
        bookingDetails.put("customerName", customerName);
        bookingDetails.put("phoneNumber", phoneNumber);
        bookingDetails.put("bookingDate", bookingDate);
        bookingDetails.put("selectedTime", selectedTime);
        bookingDetails.put("status", status);

        // Push the booking details to Firebase
        userBookingsRef.push().setValue(bookingDetails, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("CustomerCartActivity", "Error saving booking: " + databaseError.getMessage());
                    Toast.makeText(CustomerCartActivity.this, "Error saving booking", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerCartActivity.this, "Booking saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
