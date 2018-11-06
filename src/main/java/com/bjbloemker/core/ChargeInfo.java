package com.bjbloemker.core;
/*
motorcycle (required): first element of the array is for in-state license plates and the second is for out of state
car (required): first element of the array is for in-state license plates and the second is for out of state
rv (required): first element of the array is for in-state license plates and the second is for out of state
 */

import java.util.Arrays;

public class ChargeInfo {
    double motorcyclePrice[];
    double carPrice[];
    double rvPrice[];

    public ChargeInfo(double motorcyclePrice[], double carPrice[], double rvPrice[]) {
        this.motorcyclePrice = motorcyclePrice;
        this.carPrice = carPrice;
        this.rvPrice = rvPrice;
    }

    public ChargeInfo() {
        this.motorcyclePrice = null;
        this.carPrice = null;
        this.rvPrice = null;
    }

    public void setMotorcyclePrice(double[] motorcyclePrice) {
        this.motorcyclePrice = motorcyclePrice;
    }

    public void setCarPrice(double[] carPrice) {
        this.carPrice = carPrice;
    }

    public void setRvPrice(double[] rvPrice) {
        this.rvPrice = rvPrice;
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
