package com.example.shop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookStoreActivity extends AppCompatActivity {

    private EditText storeServiceNameEditText, storePriceEditText, storePreferenceEditText, whichStore, storeCusNameEditText, cartNumEditText, storeCusDateEditText;
    private CheckBox checkBox9_30AM, checkBox10AM, checkBox2PM, checkBox4_30PM;
    private Button submitButton;

    private DatabaseReference mDatabase;
    private String userId;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store);

        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        storeServiceNameEditText = findViewById(R.id.storeService_name);
        storePriceEditText = findViewById(R.id.storePrice_name);
        storePreferenceEditText = findViewById(R.id.storePreference);
        whichStore = findViewById(R.id.whichStore);
        storeCusNameEditText = findViewById(R.id.storeCus_Name);
        cartNumEditText = findViewById(R.id.cart_Num);
        storeCusDateEditText = findViewById(R.id.storeCus_Date);
        checkBox9_30AM = findViewById(R.id.checkBox3);
        checkBox10AM = findViewById(R.id.checkBox4);
        checkBox2PM = findViewById(R.id.checkBox5);
        checkBox4_30PM = findViewById(R.id.checkBox6);
        submitButton = findViewById(R.id.submit_btn);

        // Retrieve userId from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String customerId = intent.getStringExtra("customerId");
            userId = intent.getStringExtra("userId");
            if (customerId != null && userId != null) {
                // Now you have the customerId and userId, you can use them as needed
                Log.d("BookStoreActivity", "CustomerId: " + customerId);
                Log.d("BookStoreActivity", "UserId: " + userId);
            } else {
                // Handle if customerId or userId is null
                Log.e("BookStoreActivity", "CustomerId or UserId is null");
                Toast.makeText(this, "Customer ID or User ID is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle if intent is null
            Log.e("BookStoreActivity", "Intent is null");
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }

        // Initialize calendar and date picker dialog
        calendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        // Set click listener for the date EditText
        storeCusDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookStoreActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBooking();
            }
        });

        // Retrieve service name, store price, and owner ID from intent extras
        String serviceName = getIntent().getStringExtra("serviceName");
        String storePrice = getIntent().getStringExtra("storePrice");

        // Set retrieved values to the corresponding EditTexts
        whichStore.setText(getIntent().getStringExtra("storeName"));
        storeServiceNameEditText.setText(serviceName);
        storePriceEditText.setText(storePrice);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDate() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        storeCusDateEditText.setText(sdf.format(calendar.getTime()));
    }

    private void submitBooking() {
        // Get input values
        String serviceName = storeServiceNameEditText.getText().toString().trim();
        String storePrice = storePriceEditText.getText().toString().trim();
        String storePreference = storePreferenceEditText.getText().toString().trim();
        String storeName = whichStore.getText().toString().trim();
        String customerName = storeCusNameEditText.getText().toString().trim();
        String phoneNumber = cartNumEditText.getText().toString().trim();
        String bookingDate = storeCusDateEditText.getText().toString().trim();
        String selectedTime = "";

        // Determine selected time
        if (checkBox9_30AM.isChecked()) {
            selectedTime = "9:30 AM";
        } else if (checkBox10AM.isChecked()) {
            selectedTime = "10:00 AM";
        } else if (checkBox2PM.isChecked()) {
            selectedTime = "2:00 PM";
        } else if (checkBox4_30PM.isChecked()) {
            selectedTime = "4:30 PM";
        }

        // Validate inputs
        if (serviceName.isEmpty() || storePrice.isEmpty() || storePreference.isEmpty() || storeName.isEmpty() || customerName.isEmpty() || phoneNumber.isEmpty() || bookingDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Generate a unique booking ID
            String customerId = getIntent().getStringExtra("customerId");
            String bookingId = mDatabase.child("Customer").child(customerId).child("bookings").push().getKey();

            // Save booking details to Firebase along with userId
            DatabaseReference bookingsRef = mDatabase.child("Customer").child(customerId).child("bookings").child(bookingId);
            bookingsRef.child("bookingId").setValue(bookingId); // Save booking ID
            bookingsRef.child("serviceName").setValue(serviceName);
            bookingsRef.child("storePrice").setValue(storePrice);
            bookingsRef.child("storePreference").setValue(storePreference);
            bookingsRef.child("whichStore").setValue(storeName);
            bookingsRef.child("customerName").setValue(customerName);
            bookingsRef.child("phoneNumber").setValue(phoneNumber);
            bookingsRef.child("bookingDate").setValue(bookingDate);
            bookingsRef.child("selectedTime").setValue(selectedTime);
            bookingsRef.child("status").setValue("pending");
            bookingsRef.child("userId").setValue(userId); // Save userId
            bookingsRef.child("customerId").setValue(customerId);

            Toast.makeText(this, "Booking submitted successfully", Toast.LENGTH_SHORT).show();
            // Clear input fields after submission
            clearFields();
        }
    }

    // Method to clear input fields after submission
    private void clearFields() {
        storeServiceNameEditText.setText("");
        storePriceEditText.setText("");
        storePreferenceEditText.setText("");
        whichStore.setText("");
        storeCusNameEditText.setText("");
        cartNumEditText.setText("");
        storeCusDateEditText.setText("");
        checkBox9_30AM.setChecked(false);
        checkBox10AM.setChecked(false);
        checkBox2PM.setChecked(false);
        checkBox4_30PM.setChecked(false);
    }
}
