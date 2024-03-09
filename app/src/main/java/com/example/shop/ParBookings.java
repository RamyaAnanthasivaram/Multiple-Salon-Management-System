package com.example.shop;

import android.os.Parcel;
import android.os.Parcelable;

    public class ParBookings implements Parcelable {
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
        public ParBookings() {
            // Default constructor required for Parcelable
        }

        public ParBookings(String serviceName, String storePrice, String storePreference, String whichStore, String customerName, String phoneNumber, String bookingDate, String selectedTime, String userId, String bookingId) {
            this.serviceName = serviceName;
            this.storePrice = storePrice;
            this.storePreference = storePreference;
            this.whichStore = whichStore;
            this.customerName = customerName;
            this.phoneNumber = phoneNumber;
            this.bookingDate = bookingDate;
            this.selectedTime = selectedTime;
            this.userId = userId;
            this.bookingId = bookingId;
        }

        // Add your constructors here

        // Add your getters and setters here


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
        protected ParBookings(Parcel in) {
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

        public static final Creator<ParBookings> CREATOR = new Creator<ParBookings>() {
            @Override
            public ParBookings createFromParcel(Parcel in) {
                return new ParBookings(in);
            }

            @Override
            public ParBookings[] newArray(int size) {
                return new ParBookings[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }
    }



