package com.bjbloemker.api;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public abstract class ParkObj {
    protected UUID pid;

    @SerializedName("location_info")
    protected LocationInfoObj locationInfo;

    @SerializedName("payment_info")
    protected ChargeInfoObj paymentInfo;

    public ParkObj(UUID PID, LocationInfoObj location_info, ChargeInfoObj payment_info) {
        this.pid = PID;
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }

    public ParkObj(LocationInfoObj location_info, ChargeInfoObj payment_info) {
        this.pid = UUID.randomUUID();
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
        return pid.toString();
    }



}
