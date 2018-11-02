package com.bjbloemker.client;

public class CreditCard {
    private String name;
    private String address;
    private Long number;
    private int cvv;


    public CreditCard(String name, String address, Long number, int cvv) {
        this.name = name;
        this.address = address;
        setNumber(number);
        setCVV(cvv);
    }


    public void setNumber(long number) {
        String cardNumber = String.valueOf(number);

        if(cardNumber.length() == 16)
            this.number = number;
        else
            throw new IllegalArgumentException("Credit card number must be 16 digits long");

    }

    public void setCVV(int cvv){
        String cardNumber = String.valueOf(cvv);

        if(cardNumber.length() == 3 || cardNumber.length() == 3  )
            this.cvv = cvv;
        else
            throw new IllegalArgumentException("CVV number must be 3 or 4 digits long");

    }



}
