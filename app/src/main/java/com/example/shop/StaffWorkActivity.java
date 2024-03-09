package com.example.shop;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StaffWorkActivity extends AppCompatActivity {

    private LinearLayout containerLayout;

    private DatabaseReference staffWorkRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_work);

        containerLayout = findViewById(R.id.containerLayout);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        staffId = currentUser.getUid();

        staffWorkRef = FirebaseDatabase.getInstance().getReference().child("staff").child(staffId).child("StaffWork");
        retrieveDataFromFirebase();
    }

    private void retrieveDataFromFirebase() {
        staffWorkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containerLayout.removeAllViews(); // Clear existing views
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Inflate item_staff_work.xml for each entry
                        View itemView = LayoutInflater.from(StaffWorkActivity.this).inflate(R.layout.staffworkrecycle, containerLayout, false);
                        // Find views in the inflated item layout
                        TextView customerNameTextView = itemView.findViewById(R.id.customerNameTV);
                        TextView customerPhoneTextView = itemView.findViewById(R.id.customerPhoneTV);
                        TextView preferenceTextView = itemView.findViewById(R.id.preferenceTV);
                        TextView dateTextView = itemView.findViewById(R.id.dateTV);
                        TextView timeTextView = itemView.findViewById(R.id.timeTV);
                        TextView serviceTextView = itemView.findViewById(R.id.serviceTV);

                        // Set data to TextViews
                        customerNameTextView.setText(snapshot.child("customerName").getValue(String.class));
                        customerPhoneTextView.setText(snapshot.child("customerPhoneNumber").getValue(String.class));
                        preferenceTextView.setText(snapshot.child("preference").getValue(String.class));
                        dateTextView.setText(snapshot.child("date").getValue(String.class));
                        timeTextView.setText(snapshot.child("time").getValue(String.class));
                        serviceTextView.setText(snapshot.child("serviceName").getValue(String.class));

                        // Add the inflated item to the containerLayout
                        containerLayout.addView(itemView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
                Toast.makeText(StaffWorkActivity.this, "Failed to retrieve data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
