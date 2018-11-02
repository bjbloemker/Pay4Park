package com.bjbloemker.client;

import java.util.ArrayList;

public class Storage {
    //mock API class, just to be able to retrieve data from for testing purposes
    private static ArrayList<Park> parks = new ArrayList<Park>();
    private static ArrayList<Visit> visits = new ArrayList<Visit>();

    public static ArrayList<Park> getParks() {
        return parks;
    }

    public static Park getParkByName(String name) {
        for(Park p : getParks())
            if(p.getName().equalsIgnoreCase(name))
                return p;
        throw new ArrayIndexOutOfBoundsException("com.bjbloemker.client.Park with name: " + name +" not found");
    }

    public static ArrayList<Visit> getVisits() {
        return visits;
    }


}
