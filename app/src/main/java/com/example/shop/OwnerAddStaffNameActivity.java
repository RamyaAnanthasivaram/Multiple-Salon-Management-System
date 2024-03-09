package com.example.shop;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerAddStaffNameActivity extends AppCompatActivity {

    private EditText editText;
    private DatabaseReference staffNamesRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_staff_name);

        editText = findViewById(R.id.editText);
        Button ownerAddButton = findViewById(R.id.ownerAdd);

        // Set toolbar as the support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Firebase setup
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            staffNamesRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("ownerAddedStaffNames");
        }

        // Save staff name to Firebase when submit button is clicked
        ownerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStaffNameToFirebase();
            }
        });
    }

    private void saveStaffNameToFirebase() {
        String staffName = editText.getText().toString().trim();
        if (!staffName.isEmpty()) {
            // Save staff name under the current user node in Firebase
            staffNamesRef.push().setValue(staffName);
            // Display a success message or perform any other actions
            editText.setText(""); // Clear the editText after saving
            // Notify the user that the name has been added
            // You can show a toast or any other notification here
            Toast.makeText(OwnerAddStaffNameActivity.this, "Staff name added successfully", Toast.LENGTH_SHORT).show();

        } else {
            // Handle case where staff name is empty
            // You can show an error message to the user
            Toast.makeText(OwnerAddStaffNameActivity.this, "Please enter a staff name", Toast.LENGTH_SHORT).show();

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
