package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView customers,owners,staffs,admins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        customers = findViewById(R.id.customer);
        owners = findViewById(R.id.owner);
        staffs = findViewById(R.id.staff);
        admins =findViewById(R.id.admin);


        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });

        staffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, StaffLoginActivity.class);
                startActivity(intent);
            }
        });
        owners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,OwnerLoginActivity.class);
                startActivity(intent);
            }
        });
        admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }



    }


