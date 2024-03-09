// CusHomeService.java
package com.example.shop;

public class CusHomeService {
    private String serviceName;
    private String homePrice;
    private String imageUrl; // New field for the image URL

    // Default constructor (required by Firebase)
    public CusHomeService() {
    }

    public CusHomeService(String serviceName, String homePrice, String imageUrl) {
        this.serviceName = serviceName;
        this.homePrice = homePrice;
        this.imageUrl = imageUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHomePrice() {
        return homePrice;
    }

    public void setHomePrice(String homePrice) {
        this.homePrice = homePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
