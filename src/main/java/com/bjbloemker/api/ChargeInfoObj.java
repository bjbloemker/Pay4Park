package com.bjbloemker.api;

public abstract class ChargeInfoObj {
    protected double motorcyclePrice[];
    protected double carPrice[];
    protected double rvPrice[];

    public ChargeInfoObj(double motorcyclePrice[], double carPrice[], double rvPrice[]) {
        this.motorcyclePrice = motorcyclePrice;
        this.carPrice = carPrice;
        this.rvPrice = rvPrice;
    }

    public ChargeInfoObj() {
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
}
