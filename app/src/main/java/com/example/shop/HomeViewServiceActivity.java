// HomeViewServiceActivity.java
package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeViewServiceAdapter adapter;
    private List<CusHomeService> serviceList;
    private ImageView storeImageView;
    private ImageView homeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view_service);

        recyclerView = findViewById(R.id.HomerecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeImageView = findViewById(R.id.frontStore);
        homeImageView = findViewById(R.id.home_service_go);

        serviceList = new ArrayList<>();
        adapter = new HomeViewServiceAdapter(this, serviceList);
        recyclerView.setAdapter(adapter);

        // Get userId passed from CustomerHomeActivity
        String userId = getIntent().getStringExtra("userId");

        // Navigate to the services node under the specified user
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        DatabaseReference servicesRef = userRef.child("homeServices");

        servicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot serviceSnapshot : snapshot.getChildren()) {
                    // Retrieve service details and add to the list
                    CusHomeService service = serviceSnapshot.getValue(CusHomeService.class);
                    serviceList.add(service);
                }
                // Notify adapter of changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
                Toast.makeText(HomeViewServiceActivity.this, "Failed to retrieve services", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for store ImageView
        homeImageView.setOnClickListener(v -> {
            // Handle click on store ImageView (no action required)
            Toast.makeText(HomeViewServiceActivity.this, "You are already in the Home view", Toast.LENGTH_SHORT).show();
        });

        // Set click listener for home ImageView
        storeImageView.setOnClickListener(v -> {
            // Handle click on home ImageView (navigate to HomeViewServiceActivity)
            Intent intent = new Intent(HomeViewServiceActivity.this, ViewServiceActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }
}
