package com.bjbloemker.api;

import java.util.UUID;

public abstract class VehicleObj {

    protected String state;
    protected String plate;
    protected String type;

    public VehicleObj(String state, String plate, String type) {
        this.state = state;
        this.plate = plate;
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public String getPlate() {
        return plate;
    }

    public String getType() {
        return type;
    }
}
