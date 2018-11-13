package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.bjbloemker.exceptions.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonParser {

    public static LocationInfo JsonToLocation(JsonObject locationJson) throws NullAddressException, NullNameException, NullPhoneException, NullWebException, NullGeoException {
        LocationInfoObj location = new LocationInfo();

        try {
            location.setAddress(locationJson.get("address").getAsString());
        }catch (NullPointerException e){
            throw new NullAddressException("Address cannot be null");
        }
        try {
            location.setName(locationJson.get("name").getAsString());
        }catch (NullPointerException e){
            throw new NullNameException("Name cannot be null");
        }

        try{
            location.setRegion(locationJson.get("region").getAsString());
        }catch (NullPointerException e){
            location.setRegion("");
        }

        try{
            location.setPhone(locationJson.get("phone").getAsString());
        }catch (NullPointerException e){
            throw new NullPhoneException("Name cannot be null");
        }

        try {
            location.setWeb(locationJson.get("web").getAsString());
        }catch(NullPointerException e){
            throw new NullWebException("Web cannot be null");
        }

        JsonObject geoJson = null;
        try {
            geoJson = locationJson.get("geo").getAsJsonObject();
        }catch (NullPointerException e){
            throw new NullGeoException("Geo cords cannot be null");
        }
        GeoCordsObj geoCords = JsonToGeoCords(geoJson);
        location.setGeo(geoCords);

        return (LocationInfo) location;
    }

    public static GeoCords JsonToGeoCords(JsonObject geoJson) throws NullGeoException {
        GeoCordsObj geoCords = new GeoCords();
        try {
            geoCords.setLat(geoJson.get("lat").getAsFloat());
            geoCords.setLng(geoJson.get("lng").getAsFloat());
        }catch (NullPointerException e){
            throw new NullGeoException("Geo cords cannot be null");
        }
        return (GeoCords) geoCords;
    }


    public static ChargeInfo JsonToChargeInfo(JsonObject locationJson) throws InvalidPriceException{
        ChargeInfoObj chargeInfo = new ChargeInfo();

        int [] motorcyclePrices = new int[2];
        JsonArray motorcyclePricesAsJsonArray = locationJson.get("motorcycle").getAsJsonArray();
        for(int i = 0; i < motorcyclePrices.length; i++)
            motorcyclePrices[i] = motorcyclePricesAsJsonArray.get(i).getAsInt();

        int [] carPrices = new int[2];
        JsonArray carPricesAsJsonArray = locationJson.get("car").getAsJsonArray();
        for(int i = 0; i < carPrices.length; i++)
            carPrices[i] = carPricesAsJsonArray.get(i).getAsInt();

        int [] rvPrices = new int[2];
        JsonArray rvPricesAsJsonArray = locationJson.get("rv").getAsJsonArray();
        for(int i = 0; i < rvPrices.length; i++)
            rvPrices[i] = rvPricesAsJsonArray.get(i).getAsInt();

        //validate prices
        for(int i = 0; i < motorcyclePrices.length; i++)
            if(motorcyclePrices[i] < 0)
                throw new InvalidPriceException("Motorcycle prices must not be negative");

        for(int i = 0; i < carPrices.length; i++)
            if(carPrices[i] < 0)
                throw new InvalidPriceException("Car prices must not be negative");

        for(int i = 0; i < rvPrices.length; i++)
            if(rvPrices[i] < 0)
                throw new InvalidPriceException("RV prices must not be negative");


        chargeInfo.setMotorcycle(motorcyclePrices);
        chargeInfo.setCar(carPrices);
        chargeInfo.setRv(rvPrices);

        return (ChargeInfo) chargeInfo;
    }


    public static Vehicle JsonToVehicle(JsonObject vehicleJson){
        String state = vehicleJson.get("state").getAsString();
        String plate = vehicleJson.get("plate").getAsString();
        String type = vehicleJson.get("type").getAsString();

        VehicleObj vehicle = new Vehicle(state, plate, type);
        return (Vehicle) vehicle;
    }

    public static Visitor JsonToVisitor(JsonObject visitorJson){
        String name = visitorJson.get("name").getAsString();
        String email = visitorJson.get("email").getAsString();

        JsonObject paymentInfoAsJsonObject = visitorJson.get("payment_info").getAsJsonObject();
        PaymentInfoObj paymentInfo = JsonToPaymentInfo(paymentInfoAsJsonObject);

        VisitorObj visitor = new Visitor(name, email, (PaymentInfo) paymentInfo);
        return (Visitor) visitor;
    }

    public static PaymentInfo JsonToPaymentInfo(JsonObject paymentInfoJson){
        String card = paymentInfoJson.get("card").getAsString();
        String nameOnCard = paymentInfoJson.get("name_on_card").getAsString();
        String experation = paymentInfoJson.get("expiration_date").getAsString();
        int zip = paymentInfoJson.get("zip").getAsInt();
        PaymentInfoObj paymentInfo = new PaymentInfo(card, nameOnCard, experation, zip);
        return (PaymentInfo) paymentInfo;
    }
}
