package com.bjbloemker.core;
/*
"payment_info": {
          "card": "4388567890987654",
          "name_on_card": "John Doe",
          "expiration_date": "12/19",
          "zip": 60616
        }
 */
public class PaymentInfo {
    private long card;
    private String nameOnCard;
    private String expirationDate;
    private int zip;

    public PaymentInfo(long card, String nameOnCard, String expirationDate, int zip) {
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
        this.zip = zip;
    }

    public PaymentInfo() {
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

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "card=" + card +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", zip=" + zip +
                '}';
    }
}
