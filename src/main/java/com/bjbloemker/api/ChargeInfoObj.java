package com.bjbloemker.api;

public abstract class ChargeInfoObj {
    protected int motorcycle[];
    protected int car[];
    protected int rv[];

    public ChargeInfoObj(int motorcycle[], int car[], int rv[]) {
        this.motorcycle = motorcycle;
        this.car = car;
        this.rv = rv;
    }

    public ChargeInfoObj() {
        this.motorcycle = null;
        this.car = null;
        this.rv = null;
    }

    public void setMotorcycle(int[] motorcycle) {
        this.motorcycle = motorcycle;
    }

    public void setCar(int[] car) {
        this.car = car;
    }

    public void setRv(int[] rv) {
        this.rv = rv;
    }
}
