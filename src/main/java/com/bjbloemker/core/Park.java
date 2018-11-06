package com.bjbloemker.core;

import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.api.ParkObj;

import java.util.Random;
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

public class Park extends ParkObj{

    public Park(UUID PID, LocationInfoObj location_info, ChargeInfoObj payment_info) {
        super(PID, location_info, payment_info);
    }
    public Park(LocationInfoObj location_info, ChargeInfoObj payment_info) {
        super(location_info, payment_info);
    }
    public Park() {
        super(UUID.randomUUID(), null, null);
    }

    public void setLocationInfo(LocationInfoObj locationInfo) {
        super.locationInfo = locationInfo;
    }

    public String getPIDAsString(){
        return PID.toString();
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
