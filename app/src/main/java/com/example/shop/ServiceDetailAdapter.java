package com.example.shop;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class ServiceDetailAdapter extends RecyclerView.Adapter<ServiceDetailAdapter.ServiceViewHolder> {

    private Context context;
    private List<Service> serviceList;

    public ServiceDetailAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);

        // Set image using Picasso library
        Picasso.get().load(service.getImageUrl()).into(holder.imageView);

        holder.serviceNameTextView.setText(service.getServiceName());
        holder.storePriceTextView.setText("Store Price: " + service.getStorePrice());

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView serviceNameTextView, storePriceTextView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            storePriceTextView = itemView.findViewById(R.id.storePriceTextView);

        }
    }
}
