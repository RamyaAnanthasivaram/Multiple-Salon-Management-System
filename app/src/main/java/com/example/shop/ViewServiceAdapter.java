package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewServiceAdapter extends RecyclerView.Adapter<ViewServiceAdapter.ServiceViewHolder> {

    private final String userId;
    private final String storeName; // Added storeName field
    private final String customerId; // Added customerId field
    private Context context;
    private List<CusService> serviceList;

    public ViewServiceAdapter(Context context, List<CusService> serviceList, String userId, String storeName, String customerId) {
        this.context = context;
        this.serviceList = serviceList;
        this.userId = userId;
        this.storeName = storeName;
        this.customerId = customerId;
        Log.d("ViewServiceAdapter", "UserId: " + userId); // Log userId
        Log.d("ViewServiceAdapter", "CustomerId: " + customerId); // Log customerId
        Log.d("ViewServiceAdapter", "Store Name: " + storeName); // Log storeName
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cusser, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        CusService service = serviceList.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        private ImageView serviceImageView;
        private TextView serviceNameTextView;
        private TextView serviceStoreTextView;
        private Button bookStoreButton;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            serviceImageView = itemView.findViewById(R.id.imageView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            serviceStoreTextView = itemView.findViewById(R.id.storePriceTextView);
            bookStoreButton = itemView.findViewById(R.id.bookStoreButton);

            // Set click listener for the bookStoreButton
            bookStoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve service details
                    CusService service = serviceList.get(getAdapterPosition());
                    String serviceName = service.getServiceName();
                    String storePrice = service.getStorePrice();

                    // Open BookStoreActivity and pass service details
                    Intent f = new Intent(context, BookStoreActivity.class);
                    f.putExtra("serviceName", serviceName);
                    f.putExtra("storePrice", storePrice);
                    f.putExtra("storeName", storeName); // Pass storeName to BookStoreActivity
                    f.putExtra("customerId", customerId); // Pass customerId to BookStoreActivity
                    f.putExtra("userId", userId); // Pass userId to BookStoreActivity
                    context.startActivity(f);
                }
            });
        }

        public void bind(CusService service) {
            // Load image using Picasso
            Picasso.get().load(service.getImageUrl()).into(serviceImageView);
            serviceNameTextView.setText(service.getServiceName());
            serviceStoreTextView.setText("Store Price: " + service.getStorePrice());
        }
    }
}
