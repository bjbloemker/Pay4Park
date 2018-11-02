package com.bjbloemker.core;

public class OrderInteractor {
    String oid;
    VehicleInteractor vehicle;
    VisitorInteractor visitor;

    public OrderInteractor(String oid, VehicleInteractor vehicle, VisitorInteractor visitor) {
        this.oid = oid;
        this.vehicle = vehicle;
        this.visitor = visitor;
    }
}
