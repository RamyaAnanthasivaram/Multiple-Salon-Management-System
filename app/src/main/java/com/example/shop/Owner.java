package com.example.shop;

public class Owner {
    private String ownerName;
    private String storeName;
    private String phone;
    private String address;
    private String email;

    public Owner() {
        // Default constructor required for calls to DataSnapshot.getValue(Owner.class)
    }

    public Owner(String ownerName, String storeName, String phone, String address, String email) {
        this.ownerName = ownerName;
        this.storeName = storeName;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
