package com.bjbloemker.client;

import java.util.Date;
import java.util.UUID;

public class Visitor {

    Vehicle vehicle;
    CreditCard creditCard;
    private UUID id;

    public Visitor(Vehicle vehicle){
        this.vehicle = vehicle;
        id = UUID.randomUUID();
    }

    public void visitPark(Park park, Date date){
        Visit v = new Visit(park, this, date);
        Storage.getVisits().add(v);
    }

}
