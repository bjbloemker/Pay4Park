package com.bjbloemker.api;

import com.bjbloemker.core.PaymentInfo;
import com.google.gson.annotations.SerializedName;
import com.sun.xml.internal.ws.developer.Serialization;

import java.util.UUID;

public abstract class VisitorObj {

    protected UUID vid;
    protected String name;
    protected String email;
    @SerializedName("payment_info")
    protected PaymentInfo paymentInfo;

    public VisitorObj(String name, String email, PaymentInfo paymentInfo) {
        this.vid = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.paymentInfo = paymentInfo;
    }

    public String getVIDAsString() {
        return vid.toString();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
}
