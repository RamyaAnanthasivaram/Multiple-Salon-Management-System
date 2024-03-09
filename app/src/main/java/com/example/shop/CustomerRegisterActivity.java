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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, phoneNoEditText, emailEditText, passwordEditText, addressEditText;
    private Button signUpButton;
    private TextView loginLinkTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        mAuth = FirebaseAuth.getInstance();

        usernameEditText = findViewById(R.id.cu_reg_name);
        phoneNoEditText = findViewById(R.id.cu_reg_phoneNo);
        emailEditText = findViewById(R.id.cu_reg_email);
        passwordEditText = findViewById(R.id.cu_reg_password);
        addressEditText = findViewById(R.id.cu_reg_address);
        signUpButton = findViewById(R.id.cu_reg_btn);
        loginLinkTextView = findViewById(R.id.cu_log_link_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the login activity
                // Add your navigation logic here
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String phoneNo = phoneNoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Enter username");
            return;
        }

        if (TextUtils.isEmpty(phoneNo)) {
            phoneNoEditText.setError("Enter phone number");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Enter email address");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Enter password");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            addressEditText.setError("Enter address");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Save customer details to Firebase Database
                            DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                            String userId = user.getUid();
                            Customer customer = new Customer(userId, username, phoneNo, email, address);
                            customersRef.child(userId).setValue(customer)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Customer details saved successfully
                                                Toast.makeText(CustomerRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                                // Navigate to the next activity
                                                // For example:
                                                 startActivity(new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class));
                                                 finish();
                                                Intent loginIntent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);

                                                startActivity(loginIntent);
                                                finish();
                                            } else {
                                                // Failed to save customer details
                                                Toast.makeText(CustomerRegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // User with the same email already exists
                                emailEditText.setError("User with this email already exists");
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // Invalid email or password format
                                emailEditText.setError("Invalid email address");
                                passwordEditText.setError("Invalid password");
                            } else {
                                // Other error
                                Toast.makeText(CustomerRegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
