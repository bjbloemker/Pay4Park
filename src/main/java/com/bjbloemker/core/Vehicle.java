package com.bjbloemker.core;

import com.bjbloemker.api.VehicleObj;

/*
"vehicle": {
        "state": "IL",
        "plate": "GOCUBS",
        "type": "car"
      },
 */
public class Vehicle extends VehicleObj{

    public Vehicle(String state, String plate, String type) {
        super(state, plate, type);
    }

}
