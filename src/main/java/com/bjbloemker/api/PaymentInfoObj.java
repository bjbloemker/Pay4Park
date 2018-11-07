package com.bjbloemker.api;

public abstract class PaymentInfoObj {
    protected long card;
    protected String nameOnCard;
    protected String expirationDate;
    protected int zip;

    public PaymentInfoObj(long card, String nameOnCard, String expirationDate, int zip) {
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
        this.zip = zip;
    }

    public PaymentInfoObj() {
        this.card = -1;
        this.nameOnCard = null;
        this.expirationDate = null;
        this.zip = -1;
    }

    public void setCard(long card) {
        this.card = card;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
