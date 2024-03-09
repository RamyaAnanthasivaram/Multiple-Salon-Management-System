package com.example.shop;

public class BookStoreHelperClass {
    private String bookingId; // Added booking ID field
    private String serviceName;
    private String storePrice;
    private String preference;
    private String whichStore;
    private String cusName;
    private String cartNum;
    private String cusDate;
    private boolean checkBox1Selected;
    private boolean checkBox2Selected;
    private boolean checkBox3Selected;
    private boolean checkBox4Selected;

    // Default constructor required for Firebase
    public BookStoreHelperClass() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

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

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getWhichStore() {
        return whichStore;
    }

    public void setWhichStore(String whichStore) {
        this.whichStore = whichStore;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCartNum() {
        return cartNum;
    }

    public void setCartNum(String cartNum) {
        this.cartNum = cartNum;
    }

    public String getCusDate() {
        return cusDate;
    }

    public void setCusDate(String cusDate) {
        this.cusDate = cusDate;
    }

    public boolean isCheckBox1Selected() {
        return checkBox1Selected;
    }

    public void setCheckBox1Selected(boolean checkBox1Selected) {
        this.checkBox1Selected = checkBox1Selected;
    }

    public boolean isCheckBox2Selected() {
        return checkBox2Selected;
    }

    public void setCheckBox2Selected(boolean checkBox2Selected) {
        this.checkBox2Selected = checkBox2Selected;
    }

    public boolean isCheckBox3Selected() {
        return checkBox3Selected;
    }

    public void setCheckBox3Selected(boolean checkBox3Selected) {
        this.checkBox3Selected = checkBox3Selected;
    }

    public boolean isCheckBox4Selected() {
        return checkBox4Selected;
    }

    public void setCheckBox4Selected(boolean checkBox4Selected) {
        this.checkBox4Selected = checkBox4Selected;
    }
}
