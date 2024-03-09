package com.example.shop;

public class CusService {
    private String userId; // Add userId field
    private String serviceName;
    private String storePrice;
    private String imageUrl; // New field for the image URL

    // Default constructor required by Firebase
    public CusService() {
    }

    // Constructor with userId parameter
    public CusService(String userId) {
        this.userId = userId;
    }

    // Constructor with all fields
    public CusService(String userId, String serviceName, String storePrice, String imageUrl) {
        this.userId = userId;
        this.serviceName = serviceName;
        this.storePrice = storePrice;
        this.imageUrl = imageUrl;
    }

    // Getter and setter methods for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and setter methods for other fields
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(String storePrice) {
        this.storePrice = storePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
