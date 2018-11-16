package com.bjbloemker.api;

import com.bjbloemker.core.PaymentProcessing;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public String toString() {
        return "OrderObj{" +
                "oid='" + oid + '\'' +
                ", pid='" + pid + '\'' +
                ", date='" + date + '\'' +
                ", vehicle=" + vehicle +
                ", visitor=" + visitor +
                ", paymentProcessing=" + paymentProcessing +
                '}';
    }
}
