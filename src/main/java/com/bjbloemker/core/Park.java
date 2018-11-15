package com.bjbloemker.core;

import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.api.ParkObj;

import java.util.UUID;


public class Park extends ParkObj{

    public Park(LocationInfoObj location_info, ChargeInfoObj payment_info) {
        super(location_info, payment_info);
    }

}
