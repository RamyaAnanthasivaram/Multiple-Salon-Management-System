package com.example.shop;
import android.content.Intent;
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

public class StaffLoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.staff_login_username);
        passwordEditText = findViewById(R.id.staff_login_password);
        loginButton = findViewById(R.id.staff_login_button);
        signUpTextView = findViewById(R.id.staff_signupRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffLoginActivity.this, StaffRegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(StaffLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, redirect to main activity or do something
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // User is signed in, you can redirect to main activity or do something
                                // For example, you can finish this activity and go to the main activity
                                // Inside onComplete() method after starting the StaffWorkActivity

                                finish();
                                startActivity(new Intent(StaffLoginActivity.this,StaffWorkActivity.class));
                            }
                        } else {
                            // Login failed
                            Toast.makeText(StaffLoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
