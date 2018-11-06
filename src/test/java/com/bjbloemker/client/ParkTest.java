package com.bjbloemker.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ParkTest {

    @Test
    public void createValidCrationParkName(){
        Park p = new Park("name", 1.0);
        Assertions.assertEquals(p.getName(), "name");
    }

    @Test
    public void createValidCrationParkFee(){
        Park p = new Park("name", 1.0);
        Assertions.assertEquals(p.getAdmissionFee(), 1.0);
    }

    @Test
    public void createInvalidCrationParkFee(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Park("name", -1.0), "Fee cannot be less than zero!");
    }
}