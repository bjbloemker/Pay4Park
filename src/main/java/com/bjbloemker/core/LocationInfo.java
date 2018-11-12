package com.bjbloemker.core;

/*
"location_info": {
        "name": "Castle Rock",
        "region": "Northwestern Illinois",
        "address": "1365 W. Castle Rd, Oregon IL 61061",
        "phone": "815-732-7329",
        "web": "https://www.dnr.illinois.gov/Parks/Pages/CastleRock.aspx",
        "geo": { "lat": 41.978, "lng": -89.364 }
 },
 */

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
