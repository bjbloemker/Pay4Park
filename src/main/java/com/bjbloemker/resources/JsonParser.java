package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonParser {

    public static LocationInfo JsonToLocation(JsonObject locationJson){
        LocationInfoObj location = new LocationInfo();
        location.setAddress(locationJson.get("address").getAsString());
        location.setName(locationJson.get("name").getAsString());
        location.setRegion(locationJson.get("region").getAsString());
        location.setPhone(locationJson.get("phone").getAsString());
        location.setWeb(locationJson.get("web").getAsString());
        JsonObject geoJson = locationJson.get("geo").getAsJsonObject();
        GeoCordsObj geoCords = JsonToGeoCords(geoJson);
        location.setGeo(geoCords);
        return (LocationInfo) location;
    }

    public static GeoCords JsonToGeoCords(JsonObject geoJson){
        GeoCordsObj geoCords = new GeoCords();
        geoCords.setLat(geoJson.get("lat").getAsFloat());
        geoCords.setLng(geoJson.get("lng").getAsFloat());
        return (GeoCords) geoCords;
    }


    public static ChargeInfo JsonToChargeInfo(JsonObject locationJson){
        ChargeInfoObj chargeInfo = new ChargeInfo();

        double [] motorcyclePrices = new double[2];
        JsonArray motorcyclePricesAsJsonArray = locationJson.get("motorcycle").getAsJsonArray();
        for(int i = 0; i < motorcyclePrices.length; i++)
            motorcyclePrices[i] = motorcyclePricesAsJsonArray.get(i).getAsDouble();

        double [] carPrices = new double[2];
        JsonArray carPricesAsJsonArray = locationJson.get("car").getAsJsonArray();
        for(int i = 0; i < carPrices.length; i++)
            carPrices[i] = carPricesAsJsonArray.get(i).getAsDouble();

        double [] rvPrices = new double[2];
        JsonArray rvPricesAsJsonArray = locationJson.get("rv").getAsJsonArray();
        for(int i = 0; i < rvPrices.length; i++)
            rvPrices[i] = rvPricesAsJsonArray.get(i).getAsDouble();

        chargeInfo.setMotorcyclePrice(motorcyclePrices);
        chargeInfo.setCarPrice(carPrices);
        chargeInfo.setRvPrice(rvPrices);

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
