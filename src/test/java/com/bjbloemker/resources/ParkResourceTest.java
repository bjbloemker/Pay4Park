package com.bjbloemker.resources;

import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.Park;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


//System.out.println("============DEBUG==========\n" + data.toString() + "\n=============================");
class ParkResourceTest {
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    ParkResource ParkResource = new ParkResource();

    private static String parkName = "PARK nAmE";
    private static String newParkName = "PARK nEw NaMe";
    private static String region = "region 4";
    private static String address = "7654 Planed Vally Rd Chicago, IL";
    private static String phone = "(513) 555-9966";
    private static String web = "https://www.dnr.illinois.gov/Parks/Pages/MermetLake.aspx";
    private static float geoLat = 99.53f;
    private static float geoLng = -84.43f;

    private static double [] mPrices = {1,2};
    private static double [] cPrices = {3,4};
    private static double [] rPrices = {5,6};
    private static String cleanMArray = "[" + mPrices[0] + "," + mPrices[1] + "]";
    private static String cleanCArray = "[" + cPrices[0] + "," + cPrices[1] + "]";
    private static String cleanRArray = "[" + rPrices[0] + "," + rPrices[1] + "]";




    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
    }

    @Test
    void createPark() {
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.createPark(toSend);
        assertEquals(201, result.getStatus());

        JsonObject resultEntity = (JsonObject) parser.parse(result.getEntity().toString());
        String pid = resultEntity.get("pid").getAsString();
        deleteSpecificFromSystem(pid);
    }

    @Test
    void createParkNoName(){
        String toSend = "{\"location_info\": {\"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoRegion(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(201, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoAddr(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoPhone(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoWeb(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoGeo(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\"}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }

    @Test
    void createParkNoPaymentInfo(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}}";
        assertEquals(400, ParkResource.createPark(toSend).getStatus());
    }


    @Test
    void getParkDetail() {
        String pid = putParkInSystem();
        Response result = ParkResource.getParkDetail(pid);
        assertEquals(200, result.getStatus());
        String expected = "{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}},\"payment_info\":{\"motorcycle\":"+cleanMArray+",\"car\":"+cleanCArray+",\"rv\":"+cleanRArray+"}}";
        assertEquals(expected, result.getEntity());
        deleteSpecificFromSystem(pid);
    }

    @Test
    void getParkDetailBadID() {
        String id = "invalid id";
        assertEquals(404, ParkResource.getParkDetail(id).getStatus());
    }

    @Test
    void deletePark() {
        String pid = putParkInSystem();
        assertEquals(204, ParkResource.deletePark(pid).getStatus());
        deleteSpecificFromSystem(pid);
    }

    @Test
    void deleteParkBadID() {
        String id = "invalid id";
        assertEquals(404, ParkResource.deletePark(id).getStatus());
    }


    @Test
    void searchPark() {

        for (Iterator<ParkObj> iterator = MemoryManager.parks.iterator(); iterator.hasNext(); ) {
            iterator.remove();
        }

        String pid = putParkInSystem();
        Response result = ParkResource.searchPark("Planed");
        String expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());
        deleteSpecificFromSystem(pid);
    }


    @Test
    void searchParkNoResults() {
        String pid = putParkInSystem();
        Response result = ParkResource.searchPark("not_found_in_park_info");
        String expected = "[]";
        assertEquals(expected, result.getEntity());
        deleteSpecificFromSystem(pid);
    }

    @Test
    void createNoteWithPark() {
        //{"vid": "a81127b2-e424-4ace-b0cb-78b8c4dd4666", "title": "Great fishing", "text": "Caught a walleye here, did't really expect anything other than catfish."}
        //I am currently creating test for note with park
    }

    @Test
    void notesByPark() {
    }

    @Test
    void getNoteWithPark() {
    }

    @Test
    void updatePark() {
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+newParkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.updatePark(toSend, pid);
        assertEquals(204, result.getStatus());
        deleteSpecificFromSystem(pid);
    }

    @Test
    void updateParkNotFound() {
        String pid = "invalid_pid";
        String toSend = "{\"location_info\": {\"name\": \""+newParkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.updatePark(toSend, pid);
        assertEquals(404, result.getStatus());
    }


    @Test
    void updateParkNoName(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoRegion(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(204, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoAddr(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoPhone(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoWeb(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoGeo(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\"}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoPaymentInfo(){
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }








    private String putParkInSystem(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        ParkResource.createPark(toSend);
        //returns pid of created park
        createPark();
        int lastPos = MemoryManager.parks.size()-1;
        return MemoryManager.parks.get(lastPos).getPIDAsString();
    }

    private void deleteSpecificFromSystem(String id){
        ParkResource.deletePark(id);
    }

    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
    }

}