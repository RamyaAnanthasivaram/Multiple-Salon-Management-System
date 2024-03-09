package com.example.shop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CustomerProfileActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, emailEditText, addressEditText;
    private Button updateButton;

    private FirebaseAuth mAuth;
    private DatabaseReference customerRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24_black); // Set the custom drawable
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        customerRef = FirebaseDatabase.getInstance().getReference().child("Customer").child(currentUser.getUid());

        nameEditText = findViewById(R.id.cuspro_name);
        phoneEditText = findViewById(R.id.cuspro_phone);
        emailEditText = findViewById(R.id.cuspro_email);
        addressEditText = findViewById(R.id.cuspro_address);
        updateButton = findViewById(R.id.cusUpdate_btn);

        // Load existing profile data if available
        loadProfileData();

        // Set click listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void loadProfileData() {
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("username").getValue(String.class);
                    String phone = snapshot.child("phoneNo").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);

                    // Set user data to EditText fields
                    nameEditText.setText(name);
                    phoneEditText.setText(phone);
                    emailEditText.setText(email);
                    addressEditText.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }

    private void updateProfile() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Enter name");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("Enter phone number");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            addressEditText.setError("Enter address");
            return;
        }

        // Update user profile data in Firebase Database
        customerRef.child("username").setValue(name);
        customerRef.child("phoneNo").setValue(phone);
        customerRef.child("address").setValue(address);

        // Display success message
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity when back button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
