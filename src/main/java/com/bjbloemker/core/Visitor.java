package com.bjbloemker.core;
/*
"visitor": {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "payment_info": {
          "card": "4388567890987654",
          "name_on_card": "John Doe",
          "expiration_date": "12/19",
          "zip": 60616
        }
      }
 */
public class Visitor {
    String vid;
    String name;
    String email;
    PaymentInfo paymentInfo;

    public Visitor(String vid, String name, String email, PaymentInfo paymentInfo) {
        this.vid = vid;
        this.name = name;
        this.email = email;
        this.paymentInfo = paymentInfo;
    }
}
