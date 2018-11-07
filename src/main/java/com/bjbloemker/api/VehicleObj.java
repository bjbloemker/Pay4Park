package com.bjbloemker.api;

import java.util.UUID;

public abstract class VehicleObj {

    protected String state;
    protected String plate;
    protected String type;
    protected UUID VID;

    public VehicleObj(String state, String plate, String type) {
        this.VID = UUID.randomUUID();
        this.state = state;
        this.plate = plate;
        this.type = type;
    }


}
