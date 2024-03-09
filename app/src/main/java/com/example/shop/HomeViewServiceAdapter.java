// HomeViewServiceAdapter.java
package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeViewServiceAdapter extends RecyclerView.Adapter<HomeViewServiceAdapter.ServiceViewHolder> {

    private Context context;
    private List<CusHomeService> serviceList;

    public HomeViewServiceAdapter(Context context, List<CusHomeService> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homeviser, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        CusHomeService service = serviceList.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        private ImageView serviceImageView;
        private TextView serviceNameTextView;
        private TextView serviceHomeTextView;
        private Button bookHomeButton;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImageView = itemView.findViewById(R.id.homeimageView);
            serviceNameTextView = itemView.findViewById(R.id.homeserviceNameTextView);
            serviceHomeTextView = itemView.findViewById(R.id.homePriceTextView);
            bookHomeButton = itemView.findViewById(R.id.homebookStoreButton);

            // Set click listener for the bookHomeButton
            bookHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click on book home button
                    Intent intent = new Intent(context, BookHomeActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        public void bind(CusHomeService service) {
            // Load image using Picasso
            Picasso.get().load(service.getImageUrl()).into(serviceImageView);
            serviceNameTextView.setText(service.getServiceName());
            serviceHomeTextView.setText("Home Service Price: " + service.getHomePrice());
        }
    }
}
