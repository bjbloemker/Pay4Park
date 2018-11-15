package com.bjbloemker.resources;

import com.bjbloemker.core.MemoryManager;

public class DataForTesting {
    //this class only holds the data to be tested on

    protected static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    protected static ParkResource ParkResource = new ParkResource();
    protected static OrderResource OrderResource = new OrderResource();
    protected static VisitorsResource VisitorsResource = new VisitorsResource();
    protected static NotesResource NotesResource = new NotesResource();

    protected  String parkName = "Park Name";
    protected static String newParkName = "PARK nEw NaMe";
    protected static String region = "Northern Region";
    protected static String address = "1523 Grant Rd Greenville, IL";
    protected static String phone = "(513) 555-4020";
    protected static String web = "https://www.dnr.illinois.gov/Parks/Pages/ParkName.aspx";
    protected static float geoLat = 323.2f;
    protected static float geoLng = -99.43f;

    protected static double [] mPrices = {1,2};
    protected static double [] cPrices = {3,4};
    protected static double [] rPrices = {5,6};
    protected static String cleanMArray = "[" + mPrices[0] + "," + mPrices[1] + "]";
    protected static String cleanCArray = "[" + cPrices[0] + "," + cPrices[1] + "]";
    protected static String cleanRArray = "[" + rPrices[0] + "," + rPrices[1] + "]";

    protected static String cardNumber = "1111222233334444";
    protected static String name = "Jane A. Doe";
    protected static String expiration = "03/22";
    protected static int zipCode = 60616;
    protected static String email = "janeadoe@janesworld.com";

    protected static String state = "IL";
    protected static String plate = "ME8PL8";
    protected static String type = "rv";

    protected String title = "This is a title";
    protected String text = "This is the TeXt";
    protected String pid;


    protected String buildPark(){
        String json = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        ParkResource.createPark(json);
        int lastPos = MemoryManager.parks.size()-1;
        return MemoryManager.parks.get(lastPos).getPIDAsString();

    }
}
