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


    public static ChargeInfo JsonToChargeInfo(JsonObject locationJson) throws InvalidPriceException, NullChargeInfoException {
        ChargeInfoObj chargeInfo = new ChargeInfo();

        double [] motorcyclePrices = new double[2];

        JsonArray motorcyclePricesAsJsonArray;

        try{
            motorcyclePricesAsJsonArray = locationJson.get("motorcycle").getAsJsonArray();
        }catch (NullPointerException e){
            throw new NullChargeInfoException("Motorcycle charge info must be present");
        }
        for(int i = 0; i < motorcyclePrices.length; i++)
            motorcyclePrices[i] = motorcyclePricesAsJsonArray.get(i).getAsDouble();



        double [] carPrices = new double[2];
        JsonArray carPricesAsJsonArray;
        try{
            carPricesAsJsonArray = locationJson.get("car").getAsJsonArray();
        }catch (NullPointerException e){
            throw new NullChargeInfoException("Car charge info must be present");
        }
        for(int i = 0; i < carPrices.length; i++)
            carPrices[i] = carPricesAsJsonArray.get(i).getAsDouble();


        double [] rvPrices = new double[2];
        JsonArray rvPricesAsJsonArray;
        try{
            rvPricesAsJsonArray = locationJson.get("rv").getAsJsonArray();
        }catch (NullPointerException e){
            throw new NullChargeInfoException("RV charge info must be present");
        }
        for(int i = 0; i < rvPrices.length; i++)
            rvPrices[i] = rvPricesAsJsonArray.get(i).getAsDouble();

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


    public static Vehicle JsonToVehicle(JsonObject vehicleJson) throws NullVehicleException, InvalidVehicleTypeException {
        String state;
        String plate;
        String type;

        try{
            state = vehicleJson.get("state").getAsString();
            plate = vehicleJson.get("plate").getAsString();
            type = vehicleJson.get("type").getAsString();

        }catch (NullPointerException e){
            throw new NullVehicleException("Vehcile must contain state, plate, and type");
        }

        if(!type.equalsIgnoreCase("motorcycle") && !type.equalsIgnoreCase("car") && !type.equalsIgnoreCase("rv")){
            throw new InvalidVehicleTypeException("Vehicle type must be MOTORCYCLE, CAR, or RV");
        }

        VehicleObj vehicle = new Vehicle(state, plate, type);
        return (Vehicle) vehicle;
    }

    public static Visitor JsonToVisitor(JsonObject visitorJson) throws NullCardException, InvalidCardException, NullEmailException, InvalidEmailException {
        String email;
        String name;

        try{
            name = visitorJson.get("name").getAsString();
        }catch(NullPointerException e){
            name = "";
        }

        try {
            email = visitorJson.get("email").getAsString();
        }catch (NullPointerException e){
            throw new NullEmailException("Email was not provided");
        }


        if(email.length() == 0)
            throw new NullEmailException("Email was not provided");

        if(!email.contains("@") || email.startsWith("@") || !email.contains(".") || email.length() < 5 || email.contains(" "))
            throw new InvalidEmailException("Email provided is not valid");

        JsonObject paymentInfoAsJsonObject = visitorJson.get("payment_info").getAsJsonObject();
        PaymentInfoObj paymentInfo = null;
        paymentInfo = JsonToPaymentInfo(paymentInfoAsJsonObject);

        VisitorObj visitor = new Visitor(name, email, (PaymentInfo) paymentInfo);
        return (Visitor) visitor;
    }

    public static PaymentInfo JsonToPaymentInfo(JsonObject paymentInfoJson) throws NullCardException, InvalidCardException {

        String card;
        String nameOnCard;
        String expiration;
        int zip;
        try{
            card = paymentInfoJson.get("card").getAsString();
            nameOnCard = paymentInfoJson.get("name_on_card").getAsString();
            expiration = paymentInfoJson.get("expiration_date").getAsString();
            zip = paymentInfoJson.get("zip").getAsInt();
        }catch (NullPointerException e){
            throw new NullCardException("Card must have card number, name on card, expiration, and zip");
        }

        if((card.length() != 16 && card.length() != 15) || !card.matches("[0-9]+"))
            throw new InvalidCardException("Card must be 15 or 16 DIGITS to pass validation");


        PaymentInfoObj paymentInfo = new PaymentInfo(card, nameOnCard, expiration, zip);
        return (PaymentInfo) paymentInfo;
    }
}
