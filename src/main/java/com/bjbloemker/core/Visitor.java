package com.bjbloemker.core;

import com.bjbloemker.api.VisitorObj;

public class Visitor extends VisitorObj{
    public Visitor(String name, String email, PaymentInfo paymentInfo) {
        super(name, email, paymentInfo);
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "vid='" + vid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", paymentInfo=" + paymentInfo +
                '}';
    }
}
