package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, phoneNoEditText, emailEditText, passwordEditText, addressEditText;
    private Button registerButton;
    private TextView loginRedirectTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("admin");

        // Find views by ID
        usernameEditText = findViewById(R.id.ad_reg_name);
        phoneNoEditText = findViewById(R.id.ad_reg_phoneNo);
        emailEditText = findViewById(R.id.ad_reg_email);
        passwordEditText = findViewById(R.id.ad_reg_password);
        addressEditText = findViewById(R.id.ad_reg_address);
        registerButton = findViewById(R.id.ad_reg_btn);
        loginRedirectTextView = findViewById(R.id.ad_log_link_btn);

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Set click listener for login redirect text view
        loginRedirectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegisterActivity.this, AdminLoginActivity.class));
                finish();
            }
        });
    }

    // Method to handle user registration
    private void registerUser() {
        final String username = usernameEditText.getText().toString().trim();
        final String phoneNo = phoneNoEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AdminRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                            // Store additional user information in Firebase Database
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = mDatabase.child(userId);
                            currentUserDb.child("username").setValue(username);
                            currentUserDb.child("phoneNo").setValue(phoneNo);
                            currentUserDb.child("email").setValue(email);
                            currentUserDb.child("address").setValue(address);

                            // Redirect to login activity
                            startActivity(new Intent(AdminRegisterActivity.this, AdminLoginActivity.class));
                            finish();
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

