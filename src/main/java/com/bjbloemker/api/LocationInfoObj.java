package com.bjbloemker.api;

public abstract class LocationInfoObj {

    protected String name;
    protected String region;
    protected String address;
    protected String phone;
    protected String website;
    protected float lat, lng;

    public LocationInfoObj(String name, String region, String address, String phone, String website, float lat, float lng) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.lat = lat;
        this.lng = lng;
    }

    public abstract void setName(String name);

    public abstract void setRegion(String region);

    public abstract void setAddress(String address);

    public abstract void setPhone(String phone);

    public abstract void setWebsite(String website);

    public abstract void setLat(float lat);

    public abstract void setLng(float lng);
}
