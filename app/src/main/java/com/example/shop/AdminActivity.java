package com.example.shop;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private EditText ownerNameEditText, storeNameEditText, phoneEditText, addressEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginRedirectTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ownerNameEditText = findViewById(R.id.owner_register_ownername);
        storeNameEditText = findViewById(R.id.owner_register_storename);
        phoneEditText = findViewById(R.id.owner_register_phone);
        addressEditText = findViewById(R.id.owner_register_address);
        emailEditText = findViewById(R.id.owner_register_email);
        passwordEditText = findViewById(R.id.owner_register_password);
        registerButton = findViewById(R.id.owner_register_button);
        loginRedirectTextView = findViewById(R.id.owner_loginRedirectText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginRedirectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to login activity
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String ownerName = ownerNameEditText.getText().toString().trim();
        String storeName = storeNameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Save additional user details to Firebase Realtime Database
                                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
                                usersRef.child("ownerName").setValue(ownerName);
                                usersRef.child("storeName").setValue(storeName);
                                usersRef.child("phone").setValue(phone);
                                usersRef.child("address").setValue(address);

                                Toast.makeText(AdminActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                // You can further update UI or navigate to other activities
                            }
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(AdminActivity.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

