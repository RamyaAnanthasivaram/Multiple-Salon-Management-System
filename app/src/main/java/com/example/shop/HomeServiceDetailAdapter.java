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

public class HomeServiceDetailAdapter extends RecyclerView.Adapter<HomeServiceDetailAdapter.ServiceViewHolder> {

    private Context context;
    private List<HomeService> serviceList;

    public HomeServiceDetailAdapter(Context context, List<HomeService> serviceList) {
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
        HomeService service = serviceList.get(position);

        // Set image using Picasso library
        Picasso.get().load(service.getImageUrl()).into(holder.imageView);

        holder.serviceNameTextView.setText(service.getServiceName());
        holder.homePriceTextView.setText("Home Price: " + service.getHomePrice());

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView serviceNameTextView, homePriceTextView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            homePriceTextView = itemView.findViewById(R.id.storePriceTextView);

        }
    }
}

