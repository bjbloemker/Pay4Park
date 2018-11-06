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

    public abstract void setCard(long card);

    public abstract void setNameOnCard(String nameOnCard);

    public abstract void setExpirationDate(String expirationDate);

    public abstract void setZip(int zip);
}
