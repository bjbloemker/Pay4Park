package com.bjbloemker.client;

import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    @Test
    void createPark() {
        Admin a = new Admin();
        a.createPark("Parkapolis", 4);
        Park p = Storage.getParkByName("Parkapolis");
        Assertions.assertNotNull(p);
        Assertions.assertEquals("Parkapolis", p.getName());
        Assertions.assertEquals(4, p.getAdmissionFee());
    }

    @Test
    void changeFee() {
        Admin a = new Admin();
        a.createPark("Parkapolis", 4);
        Park p = Storage.getParkByName("Parkapolis");
        p.setAdmissionFee(5);
        Assertions.assertEquals(5, p.getAdmissionFee());
    }


}