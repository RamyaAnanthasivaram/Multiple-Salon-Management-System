package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffRegisterActivity extends AppCompatActivity {

    private EditText staffNameEditText, staffPhoneEditText, staffEmailEditText, staffAddressEditText, staffPasswordEditText;
    private Button registerButton;

    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        staffNameEditText = findViewById(R.id.staff_name);
        staffPhoneEditText = findViewById(R.id.staff_phone);
        staffEmailEditText = findViewById(R.id.staff_email);
        staffAddressEditText = findViewById(R.id.staff_address);
        staffPasswordEditText = findViewById(R.id.staff_password);
        registerButton = findViewById(R.id.staff_btn);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HOME");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStaff();
            }
        });


    }

    private void registerStaff() {
        String staffName = staffNameEditText.getText().toString().trim();
        String staffPhone = staffPhoneEditText.getText().toString().trim();
        String staffEmail = staffEmailEditText.getText().toString().trim();
        String staffAddress = staffAddressEditText.getText().toString().trim();
        String staffPassword = staffPasswordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(staffEmail, staffPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = currentUser.getUid();
                                DatabaseReference userStaffRef = mDatabase.child("users").child(userId).child("staff").child(user.getUid());
                                String staffId = userStaffRef.getKey();
                                userStaffRef.child("staffId").setValue(staffId);
                                userStaffRef.child("name").setValue(staffName);
                                userStaffRef.child("phone").setValue(staffPhone);
                                userStaffRef.child("address").setValue(staffAddress);

                                DatabaseReference staffRef = mDatabase.child("staff").child(user.getUid());
                                staffRef.child("staffId").setValue(staffId);
                                staffRef.child("name").setValue(staffName);
                                staffRef.child("phone").setValue(staffPhone);
                                staffRef.child("address").setValue(staffAddress);

                                Toast.makeText(StaffRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(StaffRegisterActivity.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
