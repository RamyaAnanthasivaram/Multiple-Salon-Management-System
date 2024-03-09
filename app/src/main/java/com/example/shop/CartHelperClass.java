package com.example.shop;

public class CartHelperClass {

    private String cartNam, cartPhone, cartDat,oriName;
    private String preference;
    private String timing;

    public CartHelperClass() {
        // Default constructor required for calls to DataSnapshot.getValue(CartHelperClass.class)
    }

    public CartHelperClass(String cartNam, String cartPhone, String cartDat, String preference, String timing,String oriNam) {
        this.cartNam = cartNam;
        this.cartPhone = cartPhone;
        this.cartDat = cartDat;
        this.preference = preference;
        this.timing = timing;
        this.oriName = oriNam;
    }

    // Getters and setters for all fields
    // ...


    public String getCartNam() {
        return cartNam;
    }

    public void setCartNam(String cartNam) {
        this.cartNam = cartNam;
    }

    public String getCartPhone() {
        return cartPhone;
    }

    public void setCartPhone(String cartPhone) {
        this.cartPhone = cartPhone;
    }

    public String getCartDat() {
        return cartDat;
    }

    public void setCartDat(String cartDat) {
        this.cartDat = cartDat;
    }

    public String getOriName() {
        return oriName;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
