package com.bjbloemker.core;
/*
motorcycle (required): first element of the array is for in-state license plates and the second is for out of state
car (required): first element of the array is for in-state license plates and the second is for out of state
rv (required): first element of the array is for in-state license plates and the second is for out of state
 */

public class ChargeInfoInteractor {
    double motorcyclePrice;
    double carPrice;
    double rvPrice;

    public ChargeInfoInteractor(double motorcyclePrice, double carPrice, double rvPrice) {
        this.motorcyclePrice = motorcyclePrice;
        this.carPrice = carPrice;
        this.rvPrice = rvPrice;
    }
}
