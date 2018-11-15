package com.bjbloemker.core;

import com.bjbloemker.api.GeoCordsObj;
import com.bjbloemker.api.LocationInfoObj;

public class LocationInfo extends LocationInfoObj {

    public LocationInfo(String name, String region, String address, String phone, String website, GeoCordsObj geo) {
        super(name, region, address, phone, website, geo);
    }

    public LocationInfo() {
        super(null, null, null, null, null, null);
    }

}
