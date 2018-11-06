package com.bjbloemker.core;

import java.util.UUID;

/**
 {
 "pid": 124,
 "location_info": {
    "name": "Castle Rock",
    "region": "Northwestern Illinois",
    "address": "1365 W. Castle Rd, Oregon IL 61061",
    "phone": "815-732-7329",
    "web": "https://www.dnr.illinois.gov/Parks/Pages/CastleRock.aspx",
    "geo": { "lat": 41.978, "lng": -89.364 }
 },
 "payment_info": {
    "motorcycle": [2, 3],
    "car": [4.50, 7],
    "rv": [7, 9.25]
 }
 }
 */

public class Park {

    private UUID PID;
    private LocationInfo locationInfo;
    private ChargeInfo paymentInfo;

    public Park(UUID PID, LocationInfo location_info, ChargeInfo payment_info) {
        this.PID = PID;
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }

    public Park(LocationInfo location_info, ChargeInfo payment_info) {
        this.PID = UUID.randomUUID();
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }
    public Park() {
        this.PID = UUID.randomUUID();
        this.locationInfo = null;
        this.paymentInfo = null;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    public void setPaymentInfo(ChargeInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public UUID getPID() {
        return PID;
    }

    public void setPID(UUID PID) {
        this.PID = PID;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public ChargeInfo getPaymentInfo() {
        return paymentInfo;
    }

    @Override
    public String toString() {
        return "Park{" +
                "PID=" + PID +
                ", locationInfo=" + locationInfo.toString() +
                ", paymentInfo=" + paymentInfo.toString() +
                '}';
    }
}
