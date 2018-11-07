package com.bjbloemker.api;

import com.bjbloemker.core.ChargeInfo;
import com.bjbloemker.core.LocationInfo;
import com.bjbloemker.core.PaymentInfo;

import java.util.UUID;

public abstract class ParkObj {
    protected UUID PID;
    protected LocationInfoObj locationInfo;
    protected ChargeInfoObj paymentInfo;

    public ParkObj(UUID PID, LocationInfoObj location_info, ChargeInfoObj payment_info) {
        this.PID = PID;
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }

    public ParkObj(LocationInfoObj location_info, ChargeInfoObj payment_info) {
        this.PID = UUID.randomUUID();
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }

    public void setLocationInfo(LocationInfoObj locationInfo) {
        this.locationInfo = locationInfo;
    }

    public LocationInfoObj getLocationInfo() {
        return this.locationInfo;
    }

    public String getPIDAsString(){
        return PID.toString();
    }



}
