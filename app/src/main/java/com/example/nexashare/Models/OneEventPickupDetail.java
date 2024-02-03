package com.example.nexashare.Models;

public class OneEventPickupDetail {
    private String pickupLocation;
    private String pickupTime;
    private int availableSeats;



    public OneEventPickupDetail() {
        // Empty constructor needed for Firestore serialization
    }

    public OneEventPickupDetail(String pickupLocation, int availableSeats, String pickupTime) {
        this.pickupLocation = pickupLocation;
        this.pickupTime = pickupTime;
        this.availableSeats = availableSeats;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
