package com.example.shop;
import android.os.Parcel;
import android.os.Parcelable;

public class Booking implements Parcelable {
    private String serviceName;
    private String storePrice;
    private String storePreference;
    private String whichStore;
    private String customerName;
    private String phoneNumber;
    private String bookingDate;
    private String selectedTime;
    private String userId;
    private String bookingId;

    // Constructors, getters, and setters

    // Default constructor
    public Booking() {
        // Required empty constructor for Firebase
    }

    public Booking(String userId) {
        this.userId = userId;
    }

    // Getters and setters
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

    public String getStorePreference() {
        return storePreference;
    }

    public void setStorePreference(String storePreference) {
        this.storePreference = storePreference;
    }

    public String getWhichStore() {
        return whichStore;
    }

    public void setWhichStore(String whichStore) {
        this.whichStore = whichStore;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    // Parcelable implementation
    protected Booking(Parcel in) {
        serviceName = in.readString();
        storePrice = in.readString();
        storePreference = in.readString();
        whichStore = in.readString();
        customerName = in.readString();
        phoneNumber = in.readString();
        bookingDate = in.readString();
        selectedTime = in.readString();
        userId = in.readString();
        bookingId = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceName);
        dest.writeString(storePrice);
        dest.writeString(storePreference);
        dest.writeString(whichStore);
        dest.writeString(customerName);
        dest.writeString(phoneNumber);
        dest.writeString(bookingDate);
        dest.writeString(selectedTime);
        dest.writeString(userId);
        dest.writeString(bookingId);
    }
}
