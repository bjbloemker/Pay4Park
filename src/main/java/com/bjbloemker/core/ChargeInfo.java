package com.bjbloemker.core;

import com.bjbloemker.api.ChargeInfoObj;

import java.util.Arrays;

public class ChargeInfo extends ChargeInfoObj{

    public ChargeInfo(double motorcyclePrice[], double carPrice[], double rvPrice[]) {
        super(motorcyclePrice, carPrice, rvPrice);
    }

    public ChargeInfo() {
        super(null, null, null);
    }

}
