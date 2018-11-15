package com.bjbloemker.api;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class PaymentProcessingObj {

    @SerializedName("card_transaction_id")
    String cardTransactionID;
    @SerializedName("date_and_time")
    String dateAndTime;

    public PaymentProcessingObj(String cardTransactionID) {
        this.cardTransactionID = cardTransactionID;
        this.dateAndTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public String getCardTransactionID() {
        return cardTransactionID;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
