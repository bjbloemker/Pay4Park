package com.bjbloemker.api;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public abstract class ParkObj {
    protected String pid;

    @SerializedName("location_info")
    protected LocationInfoObj locationInfo;

    @SerializedName("payment_info")
    protected ChargeInfoObj paymentInfo;

    public ParkObj(LocationInfoObj location_info, ChargeInfoObj payment_info) {
        this.pid = UUID.randomUUID().toString();
        this.locationInfo = location_info;
        this.paymentInfo = payment_info;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public LocationInfoObj getLocationInfo() {
        return this.locationInfo;
    }

    public String getPIDAsString(){
        return pid.toString();
    }



}
