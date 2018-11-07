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

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }//TODO: CHECK USE

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }
}
