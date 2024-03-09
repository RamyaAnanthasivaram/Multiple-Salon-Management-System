package com.example.shop;

public class HomeService {
    private String serviceName;
    private String homePrice; // Assuming this field represents the home price

    private String imageUrl;

    // Default constructor required for Firebase
    public HomeService() {
    }

    public HomeService(String serviceName, String homePrice, String imageUrl) {
        this.serviceName = serviceName;
        this.homePrice = homePrice;
        this.imageUrl = imageUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getHomePrice() {
        return homePrice; // Corrected getter method name
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
