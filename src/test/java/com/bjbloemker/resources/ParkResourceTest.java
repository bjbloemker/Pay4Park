package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
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

class ParkResourceTest {
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    private static ParkResource ParkResource = new ParkResource();
    private static OrderResource OrderResource = new OrderResource();

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

    private static String cardNumber = "1111222233334444";
    private static String name = "Jane A. Doe";
    private static String expiration = "06/23";
    private static int zipCode = 60616;
    private static String email = "email@gmail.com";

    private static String state = "IL";
    private static String plate = "PL84ME";
    private static String type = "car";

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void createPark() {
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.createPark(toSend);
        System.out.println("MAJOR DEBUG: " + result.getEntity().toString());
        assertEquals(201, result.getStatus());
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
    }

    @Test
    void deleteParkBadID() {
        String id = "invalid id";
        assertEquals(404, ParkResource.deletePark(id).getStatus());
    }


    @Test
    void searchPark() {
        String pid = putParkInSystem();
        Response result = ParkResource.searchPark("Planed");
        String expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());
    }


    @Test
    void searchParkNoResults() {
        putParkInSystem();
        Response result = ParkResource.searchPark("not_found_in_park_info");
        String expected = "[]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void createNoteWithParkInvalid() {
        String pid = putParkInSystem();
        PaymentInfoObj paymentInfo = new PaymentInfo(cardNumber, name, expiration, zipCode);
        VisitorObj visitor = new Visitor("John Doe", "john@com.org", (PaymentInfo) paymentInfo);
        String title = "This is my title";
        String text = "This is my text";
        String toSend = "{\"vid\": \""+visitor.getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        assertEquals(400, ParkResource.createNoteWithPark(toSend, pid).getStatus());
    }

    @Test
    void createNoteWithParkValid() {
        String pid = putParkInSystem();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is my title";
        String text = "This is my text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        assertEquals(201, ParkResource.createNoteWithPark(toSend, pid).getStatus());
    }

    @Test
    void notesByPark() {
        String pid = putParkInSystem();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is a title";
        String text = "This is a text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);

        NoteObj note = MemoryManager.notes.get(0);
        String expect = "[{\"pid\":\""+pid+"\",\"notes\":[{\"nid\":\""+note.getNIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+note.getTitle()+"\"}]}]";
        assertEquals(expect, ParkResource.notesByPark(pid).getEntity().toString());

    }



    @Test
    void notesByParkEmpty() {
        String pid = putParkInSystem();
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[]}]";
        assertEquals(expected, ParkResource.notesByPark(pid).getEntity().toString());
    }

    @Test
    void getNoteWithParkBadRequest() {
        assertEquals(400, ParkResource.getNoteWithPark("non_exist", "uncoorolated").getStatus());
    }

    @Test
    void getNoteWithParkNotFound() {
        String pid = putParkInSystem();
        assertEquals(400, ParkResource.getNoteWithPark(pid, "non_real_nid").getStatus());
    }



    @Test
    void getNoteWithParkOK() {
        String pid = putParkInSystem();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is a possible title";
        String text = "This is some text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);

        NoteObj note = MemoryManager.notes.get(0);
        String expect = "{\"nid\":\""+note.getNIDAsString()+"\",\"pid\":\""+pid+"\",\"vid\":\""+order.getVisitor().getVIDAsString()+"\",\"date\":\""+order.getDate()+"\",\"title\":\""+title+"\",\"text\":\""+text+"\"}";
        assertEquals(expect, ParkResource.getNoteWithPark(pid,note.getNIDAsString()).getEntity().toString());

    }

    @Test
    void updatePark() {
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+newParkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.updatePark(toSend, pid);
        assertEquals(204, result.getStatus());
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

    @Test
    void updateParkInvalidChargeData() {
        double [] localMPrices = mPrices;

        for(int i = 0; i < localMPrices.length; i++)
            localMPrices[i] = localMPrices[1]*-1;
        String localCleanMArray = "[" + localMPrices[0] + "," + localMPrices[1] + "]";
        String pid = putParkInSystem();
        String toSend = "{\"location_info\": {\"name\": \""+newParkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+localCleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.updatePark(toSend, pid);
        assertEquals(400, result.getStatus());
    }


    private String putParkInSystem(){
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        ParkResource.createPark(toSend);
        int lastPos = MemoryManager.parks.size()-1;
        assertTrue(true);
        return MemoryManager.parks.get(lastPos).getPIDAsString();

    }


    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

}