package com.bjbloemker.adapters;

import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.PaymentInfo;

/*
{
   "vid": 411,
   "name": "John Doe",
   "email": "john.doe@example.com"
}
 */
public class BasicVisitor extends VisitorObj{
    public BasicVisitor(String name, String email, PaymentInfo paymentInfo) {
        super(name, email, paymentInfo);
    }
}
