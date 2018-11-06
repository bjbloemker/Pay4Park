package com.bjbloemker.core;
/*
motorcycle (required): first element of the array is for in-state license plates and the second is for out of state
car (required): first element of the array is for in-state license plates and the second is for out of state
rv (required): first element of the array is for in-state license plates and the second is for out of state
 */

import com.bjbloemker.api.ChargeInfoObj;

import java.util.Arrays;

public class ChargeInfo extends ChargeInfoObj{

    public ChargeInfo(double motorcyclePrice[], double carPrice[], double rvPrice[]) {
        super(motorcyclePrice, carPrice, rvPrice);
    }

    public ChargeInfo() {
        super(null, null, null);
    }

    public void setMotorcyclePrice(double[] motorcyclePrice) {
        super.motorcyclePrice = motorcyclePrice;
    }

    public void setCarPrice(double[] carPrice) {
        super.carPrice = carPrice;
    }

    public void setRvPrice(double[] rvPrice) {
        super.rvPrice = rvPrice;
    }

    @Override
    public String toString() {
        return "ChargeInfo{" +
                "motorcyclePrice=" + Arrays.toString(motorcyclePrice) +
                ", carPrice=" + Arrays.toString(carPrice) +
                ", rvPrice=" + Arrays.toString(rvPrice) +
                '}';
    }
}
