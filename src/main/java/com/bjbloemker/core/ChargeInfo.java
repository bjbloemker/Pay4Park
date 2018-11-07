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



    @Override
    public String toString() {
        return "ChargeInfo{" +
                "motorcyclePrice=" + Arrays.toString(motorcyclePrice) +
                ", carPrice=" + Arrays.toString(carPrice) +
                ", rvPrice=" + Arrays.toString(rvPrice) +
                '}';
    }
}
