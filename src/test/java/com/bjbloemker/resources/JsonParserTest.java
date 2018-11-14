package com.bjbloemker.resources;

import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    Gson gson = new Gson();
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    @Test
    void jsonToLocation() throws NullPhoneException, NullGeoException, NullAddressException, NullNameException, NullWebException {
        JsonObject json = (JsonObject) parser.parse("{\"name\":\"Mermet Lake\",\"region\":\"Southern Illinois\",\"address\":\"1812 Grinnell Road, Belknap, IL 62908\",\"phone\":\"618-524-5577\",\"web\":\"https://www.dnr.illinois.gov/Parks/Pages/MermetLake.aspx\",\"geo\":{\"lat\":37.275,\"lng\":-88.849}}");
        LocationInfoObj result = JsonParser.JsonToLocation(json);
        System.out.println(result);
    }

    @Test
    void jsonToGeoCords() {
    }

    @Test
    void jsonToChargeInfo() {
    }

    @Test
    void jsonToVehicle() {
    }

    @Test
    void jsonToVisitor() {
    }

    @Test
    void jsonToPaymentInfo() {
    }

}