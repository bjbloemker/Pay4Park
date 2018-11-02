package com.bjbloemker.core;
/*
"payment_info": {
          "card": "4388567890987654",
          "name_on_card": "John Doe",
          "expiration_date": "12/19",
          "zip": 60616
        }
 */
public class PaymentInfoInteractor {
    long card;
    String nameOnCard;
    String expirationDate;
    int zip;

    public PaymentInfoInteractor(long card, String nameOnCard, String expirationDate, int zip) {
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
        this.zip = zip;
    }
}
