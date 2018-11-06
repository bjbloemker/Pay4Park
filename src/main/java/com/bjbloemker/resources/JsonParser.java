package com.bjbloemker.resources;

import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.core.ChargeInfo;
import com.bjbloemker.core.LocationInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonParser {

    public static LocationInfo JsonToLocation(JsonObject locationJson){
        LocationInfoObj location = new LocationInfo();
        location.setAddress(locationJson.get("address").getAsString());
        location.setName(locationJson.get("name").getAsString());
        location.setPhone(locationJson.get("phone").getAsString());
        location.setWebsite(locationJson.get("web").getAsString());
        JsonObject geoJson = locationJson.get("geo").getAsJsonObject();
        location.setLat(geoJson.get("lat").getAsFloat());
        location.setLng(geoJson.get("lng").getAsFloat());

        return (LocationInfo) location;
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
}
