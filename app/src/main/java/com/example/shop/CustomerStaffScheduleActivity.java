package com.example.shop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class CustomerStaffScheduleActivity extends AppCompatActivity {

    private EditText ownerGettingCustomerId, bookedStoreServiceName, bookedStorePreference, bookedWhichStore,
            bookedCusName, bookedStoreCusDate, bookedTiming;
    private Spinner spinner;
    private DatabaseReference customerAppointmentsRef;

    private String customerId;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_staff_schedule);

        // Initialize views
        ownerGettingCustomerId = findViewById(R.id.ownerGettingCustomerId);
        bookedStoreServiceName = findViewById(R.id.bookedStoreService_name);
        bookedStorePreference = findViewById(R.id.bookedStorePreference);
        bookedWhichStore = findViewById(R.id.bookedWhichStore);
        bookedCusName = findViewById(R.id.bookedCus_Name);
        bookedStoreCusDate = findViewById(R.id.bookedStoreCus_Date);
        bookedTiming = findViewById(R.id.BookedTiming);
        spinner = findViewById(R.id.spinner);
        Button bookedSubmitBtn = findViewById(R.id.bookedSubmit_btn);

        // Set toolbar as the support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Retrieve customer ID from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customerId = extras.getString("customerId");
            ownerGettingCustomerId.setText(customerId);
            bookedStoreServiceName.setText(extras.getString("serviceName"));
            bookedStorePreference.setText(extras.getString("storePreference"));
            bookedCusName.setText(extras.getString("customerName"));
            bookedStoreCusDate.setText(extras.getString("bookingDate"));
            bookedTiming.setText(extras.getString("selectedTime"));
        }

        // Initialize Firebase database reference
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            customerAppointmentsRef = FirebaseDatabase.getInstance().getReference("Customer").child(customerId).child("history");
        }

        // Load staff names from Firebase and populate the Spinner
        loadStaffNames();

        // Save appointment details to Firebase when submit button is clicked
        bookedSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointmentDetailsToFirebase();
            }
        });
    }

    private void loadStaffNames() {
        if (currentUser != null) {
            // Assuming you have staff names stored in Firebase under the "staffNames" node
            DatabaseReference staffNamesRef = FirebaseDatabase.getInstance().getReference("users")
                    .child(currentUser.getUid()).child("ownerAddedStaffNames");
            staffNamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String> staffNames = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String staffName = snapshot.getValue(String.class);
                        staffNames.add(staffName);
                    }
                    // Populate Spinner with staff names
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CustomerStaffScheduleActivity.this,
                            android.R.layout.simple_spinner_item, staffNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(CustomerStaffScheduleActivity.this,
                            "Failed to load staff names: " + databaseError.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveAppointmentDetailsToFirebase() {
        String selectedStaffName = spinner.getSelectedItem().toString();
        String appointmentKey = customerAppointmentsRef.push().getKey();
        DatabaseReference newAppointmentRef = customerAppointmentsRef.child(appointmentKey);

        newAppointmentRef.child("customerId").setValue(customerId);
        newAppointmentRef.child("serviceName").setValue(bookedStoreServiceName.getText().toString());
        newAppointmentRef.child("storePreference").setValue(bookedStorePreference.getText().toString());
        newAppointmentRef.child("storeName").setValue(bookedWhichStore.getText().toString());
        newAppointmentRef.child("customerName").setValue(bookedCusName.getText().toString());
        newAppointmentRef.child("bookingDate").setValue(bookedStoreCusDate.getText().toString());
        newAppointmentRef.child("selectedTime").setValue(bookedTiming.getText().toString());
        newAppointmentRef.child("staffName").setValue(selectedStaffName);

        Toast.makeText(this, "Appointment details saved successfully", Toast.LENGTH_SHORT).show();
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
