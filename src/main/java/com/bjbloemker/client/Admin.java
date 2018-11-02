package com.bjbloemker.client;

import java.util.ArrayList;

public class Admin {

    ArrayList<Park> parks = new ArrayList<Park>();

    public Park createPark(String name, double admissonFee){
        Park p = new Park(name, admissonFee);
        return p;
    }

    public void changeFee(Park park, double newFee){
        park.setAdmissionFee(newFee);
    }

    public ArrayList<Park> getParks() {
        return parks;
    }
}
