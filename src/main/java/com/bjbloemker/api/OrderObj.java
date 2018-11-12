package com.bjbloemker.api;

import com.bjbloemker.core.Vehicle;
import com.bjbloemker.core.Visitor;

import java.util.UUID;

public abstract class OrderObj {

    protected UUID oid;
    protected String pid;
    protected VehicleObj vehicle;
    protected VisitorObj visitor;

    public OrderObj(String pid, Vehicle vehicle, Visitor visitor) {
        this.oid = UUID.randomUUID();
        this.pid = pid;
        this.vehicle = vehicle;
        this.visitor = visitor;
    }

    public String getOIDAsString(){
        return oid.toString();
    }

    public String getPIDAsString(){
        return pid;
    }

    public VehicleObj getVehicle() {
        return vehicle;
    }

    public VisitorObj getVisitor() {
        return visitor;
    }
}
