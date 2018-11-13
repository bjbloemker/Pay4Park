package com.bjbloemker.api;

public abstract class ChargeInfoObj {
    protected double motorcycle[];
    protected double car[];
    protected double rv[];

    public ChargeInfoObj(double motorcycle[], double car[], double rv[]) {
        this.motorcycle = motorcycle;
        this.car = car;
        this.rv = rv;
    }

    public void setMotorcycle(double[] motorcycle) {
        this.motorcycle = motorcycle;
    }

    public void setCar(double[] car) {
        this.car = car;
    }

    public void setRv(double[] rv) {
        this.rv = rv;
    }

    public double[] getMotorcycle() {
        return motorcycle;
    }

    public double[] getCar() {
        return car;
    }

    public double[] getRv() {
        return rv;
    }
}
