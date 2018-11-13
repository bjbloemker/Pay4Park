package com.bjbloemker.api;

import com.bjbloemker.core.PaymentProcessing;
import com.bjbloemker.core.Vehicle;
import com.bjbloemker.core.Visitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class OrderObj {

    protected String oid;
    protected String pid;
    protected String date;
    protected VehicleObj vehicle;
    protected VisitorObj visitor;
    protected PaymentProcessingObj paymentProcessing;


    public OrderObj(String pid, VehicleObj vehicle, VisitorObj visitor) {
        this.oid = pid;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.pid = pid;
        this.vehicle = vehicle;
        this.visitor = visitor;
        this.paymentProcessing = new PaymentProcessing("123-4567-89");
    }

    public String getOIDAsString(){
        return oid.toString();
    }

    public String getPIDAsString(){
        return pid;
    }

    public String getDate() {
        return date;
    }

    public VehicleObj getVehicle() {
        return vehicle;
    }

    public VisitorObj getVisitor() {
        return visitor;
    }

    public PaymentProcessingObj getPaymentProcessing() {
        return paymentProcessing;
    }
}
