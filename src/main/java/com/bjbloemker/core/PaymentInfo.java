package com.bjbloemker.core;

import com.bjbloemker.api.PaymentInfoObj;

public class PaymentInfo extends PaymentInfoObj{

    public PaymentInfo(String card, String nameOnCard, String expirationDate, int zip) {
        super(card, nameOnCard, expirationDate, zip);
    }

    public String getHiddenCard(){
        String lastFour = card.substring(card.length()-4);
        return "xxxxxxxxxxx" + lastFour;
    }

}
