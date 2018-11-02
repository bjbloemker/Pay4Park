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

public class LocationInfoInteractor {
    String name;
    String region;
    String address;
    String phone;
    String website;
    float lat, lng;

    public LocationInfoInteractor(String name, String region, String address, String phone, String website, float lat, float lng) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.lat = lat;
        this.lng = lng;
    }

}
