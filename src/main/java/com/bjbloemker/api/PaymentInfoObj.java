package com.bjbloemker.api;

public abstract class PaymentInfoObj {
    protected String card;
    protected String nameOnCard;
    protected String expirationDate;
    protected int zip;

    public PaymentInfoObj(String card, String nameOnCard, String expirationDate, int zip) {
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
        this.zip = zip;
    }

    public String getCard() {
        return card;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getZip() {
        return zip;
    }
}
