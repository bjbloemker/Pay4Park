package com.bjbloemker.api;

public abstract class LocationInfoObj {

    protected String name;
    protected String region;
    protected String address;
    protected String phone;
    protected String web;
    protected GeoCordsObj geo;

    public LocationInfoObj(String name, String region, String address, String phone, String web, GeoCordsObj geo) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.web = web;
        this.geo = geo;
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

    public void setWeb(String web) {
        this.web = web;
    }

    public void setGeo(GeoCordsObj geo) {
        this.geo = geo;
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

    public String getWeb() {
        return web;
    }

    public GeoCordsObj getGeo() {
        return geo;
    }
}
