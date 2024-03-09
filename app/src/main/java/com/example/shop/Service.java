package com.example.shop;
public class Service {
    private String serviceName;
    private String storePrice;

    private String imageUrl;

    // Default constructor required for Firebase
    public Service() {
    }

    public Service(String serviceName, String storePrice, String homePrice, String imageUrl) {
        this.serviceName = serviceName;
        this.storePrice = storePrice;

        this.imageUrl = imageUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getStorePrice() {
        return storePrice;
    }



    public String getImageUrl() {
        return imageUrl;
    }
}
