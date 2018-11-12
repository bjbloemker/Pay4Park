package com.bjbloemker.core;

import com.bjbloemker.api.GeoCordsObj;

public class GeoCords extends GeoCordsObj {
    public GeoCords(float lat, float lng) {
        super(lat, lng);
    }

    public GeoCords(){
        super(-1,-1);
    }

}
