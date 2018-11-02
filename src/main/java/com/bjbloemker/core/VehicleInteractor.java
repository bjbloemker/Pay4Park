package com.bjbloemker.core;
/*
"vehicle": {
        "state": "IL",
        "plate": "GOCUBS",
        "type": "car"
      },
 */
public class VehicleInteractor {
    String state;
    String plate;
    String type;

    public VehicleInteractor(String state, String plate, String type) {
        this.state = state;
        this.plate = plate;
        this.type = type;
    }
}
