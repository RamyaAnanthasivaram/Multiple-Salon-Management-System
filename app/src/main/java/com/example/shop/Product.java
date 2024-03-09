// Product.java
package com.example.shop;

public class Product {
    private String serviceName;
    private String storeAmount;
    private String homeAmount;
    private String imageUrl;

    // Default constructor required for Firebase
    public Product() {
    }

    public Product(String serviceName, String storeAmount, String homeAmount, String imageUrl) {
        this.serviceName = serviceName;
        this.storeAmount = storeAmount;
        this.homeAmount = homeAmount;
        this.imageUrl = imageUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(String storeAmount) {
        this.storeAmount = storeAmount;
    }

    public String getHomeAmount() {
        return homeAmount;
    }

    public void setHomeAmount(String homeAmount) {
        this.homeAmount = homeAmount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

