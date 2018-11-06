package com.bjbloemker.core;

public class Order {
    String oid;
    Vehicle vehicle;
    Visitor visitor;

    public Order(String oid, Vehicle vehicle, Visitor visitor) {
        this.oid = oid;
        this.vehicle = vehicle;
        this.visitor = visitor;
    }
}
