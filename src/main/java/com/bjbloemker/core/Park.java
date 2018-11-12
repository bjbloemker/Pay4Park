package com.bjbloemker.core;

import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.api.ParkObj;

import java.util.UUID;


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

    @Override
    public String toString() {
        return "Park{" +
                "pid=" + pid +
                ", locationInfo=" + locationInfo.toString() +
                ", paymentInfo=" + paymentInfo.toString() +
                '}';
    }
}
