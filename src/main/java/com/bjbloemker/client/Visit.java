package com.bjbloemker.client;

import java.util.Date;

public class Visit {
    Park park;
    Visitor visitor;
    Date dateOfVisit;

    public Visit(Park park, Visitor visitor, Date dateOfVisit) {
        this.park = park;
        this.visitor = visitor;
        this.dateOfVisit = dateOfVisit;
    }
}
