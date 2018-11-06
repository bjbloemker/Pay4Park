package com.bjbloemker.core;

import com.bjbloemker.api.PaymentInfoObj;

/*
"payment_info": {
          "card": "4388567890987654",
          "name_on_card": "John Doe",
          "expiration_date": "12/19",
          "zip": 60616
        }
 */
public class PaymentInfo extends PaymentInfoObj{


    public PaymentInfo(long card, String nameOnCard, String expirationDate, int zip) {
        super(card, nameOnCard, expirationDate, zip);
    }

    public PaymentInfo() {
        super(-1,null,null, -1);
    }

    public void setCard(long card) {
        super.card = card;
    }

    public void setNameOnCard(String nameOnCard) {
        super.nameOnCard = nameOnCard;
    }

    public void setExpirationDate(String expirationDate) {
        super.expirationDate = expirationDate;
    }

    public void setZip(int zip) {
        super.zip = zip;
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
