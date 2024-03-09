package com.example.shop;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SendWorkToStaffActivity extends AppCompatActivity {

    private EditText ownerGettingStaffIdEditText;
    private EditText staffNameEditText;
    private EditText bookedStoreCusDateEditText;
    private EditText bookedTimingEditText;
    private EditText bookedStoreServiceNameEditText;
    private EditText bookedStorePreferenceEditText;
    private EditText bookedCustomerNameEditText;
    private EditText bookedStaffForCustomerEditText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_work_to_staff);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        // Initialize EditText fields
        ownerGettingStaffIdEditText = findViewById(R.id.ownerGettingStaffId);
        staffNameEditText = findViewById(R.id.staffName);
        bookedStoreCusDateEditText = findViewById(R.id.bookedStoreCus_Date);
        bookedTimingEditText = findViewById(R.id.BookedTiming);
        bookedStoreServiceNameEditText = findViewById(R.id.bookedStoreService_name);
        bookedStorePreferenceEditText = findViewById(R.id.bookedStorePreference);
        bookedCustomerNameEditText = findViewById(R.id.bookedCus_Name);
        bookedStaffForCustomerEditText = findViewById(R.id.bookedStaffForCustomer);

        // Set toolbar as the support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set click listeners for date and time EditText fields
        bookedStoreCusDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        bookedTimingEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        // Set click listener for submit button
        findViewById(R.id.bookedSubmit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitWork();
            }
        });

        // Retrieve data passed from GiveWorkToStaffActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get staff ID and staff name from extras
            String staffId = extras.getString("staffId");
            String staffName = extras.getString("staffName");

            // Set staff ID and staff name in EditText fields
            ownerGettingStaffIdEditText.setText(staffId);
            staffNameEditText.setText(staffName);
        }
    }

    // Method to show date picker
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        bookedStoreCusDateEditText.setText(date);
                    }
                },
                year, month, dayOfMonth);
        datePickerDialog.show();
    }

    // Method to show time picker
    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        bookedTimingEditText.setText(time);
                    }
                },
                hour, minute, true);
        timePickerDialog.show();
    }

    // Method to submit work details
    private void submitWork() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String staffId = ownerGettingStaffIdEditText.getText().toString().trim();
            String staffName = staffNameEditText.getText().toString().trim();
            String date = bookedStoreCusDateEditText.getText().toString().trim();
            String time = bookedTimingEditText.getText().toString().trim();
            String serviceName = bookedStoreServiceNameEditText.getText().toString().trim();
            String preference = bookedStorePreferenceEditText.getText().toString().trim();
            String customerName = bookedCustomerNameEditText.getText().toString().trim();
            String customerPhoneNumber = bookedStaffForCustomerEditText.getText().toString().trim();

            DatabaseReference staffWorkRef = mDatabase.child("users").child(userId).child("staff").child(staffId).child("StaffWork").child(serviceName);

            staffWorkRef.child("staffId").setValue(staffId);
            staffWorkRef.child("staffName").setValue(staffName);
            staffWorkRef.child("date").setValue(date);
            staffWorkRef.child("time").setValue(time);
            staffWorkRef.child("serviceName").setValue(serviceName);
            staffWorkRef.child("preference").setValue(preference);
            staffWorkRef.child("customerName").setValue(customerName);
            staffWorkRef.child("customerPhoneNumber").setValue(customerPhoneNumber);

            DatabaseReference stafffWorkRef = mDatabase.child("staff").child(staffId).child("StaffWork").child(serviceName);

            stafffWorkRef.child("staffId").setValue(staffId);
            stafffWorkRef.child("staffName").setValue(staffName);
            stafffWorkRef.child("date").setValue(date);
            stafffWorkRef.child("time").setValue(time);
            stafffWorkRef.child("serviceName").setValue(serviceName);
            stafffWorkRef.child("preference").setValue(preference);
            stafffWorkRef.child("customerName").setValue(customerName);
            stafffWorkRef.child("customerPhoneNumber").setValue(customerPhoneNumber);

            Toast.makeText(SendWorkToStaffActivity.this, "Work details submitted successfully.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after submission
        } else {
            Toast.makeText(SendWorkToStaffActivity.this, "User not authenticated.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
