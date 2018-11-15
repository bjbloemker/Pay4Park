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

class ParkResourceTest extends DataForTesting{


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
        assertEquals(201, result.getStatus());
        String expected = "{\"pid\":\""+MemoryManager.parks.get(0).getPIDAsString()+"\"}";
        assertEquals(expected, result.getEntity().toString());
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
        String pid = buildPark();
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
        String pid = buildPark();
        assertEquals(204, ParkResource.deletePark(pid).getStatus());
    }

    @Test
    void deleteParkBadID() {
        String id = "invalid id";
        assertEquals(404, ParkResource.deletePark(id).getStatus());
    }


    @Test
    void searchPark() {
        String pid = buildPark();
        Response result = ParkResource.searchPark("Grant");
        String expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //the following is for branch testing

        //name
        result = ParkResource.searchPark("park");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //phone
        result = ParkResource.searchPark("(513)");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //phone
        result = ParkResource.searchPark(".gov");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //region
        result = ParkResource.searchPark("north");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //lat
        result = ParkResource.searchPark("23.2");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

        //lng
        result = ParkResource.searchPark("9.43");
        expected = "[{\"pid\":\""+pid+"\",\"location_info\":{\"name\":\""+parkName+"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}}]";
        assertEquals(expected, result.getEntity());

    }


    @Test
    void searchParkNoResults() {
        buildPark();
        Response result = ParkResource.searchPark("not_found_in_park_info");
        String expected = "[]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void createNoteWithParkInvalid() {
        String pid = buildPark();
        PaymentInfoObj paymentInfo = new PaymentInfo(cardNumber, name, expiration, zipCode);
        VisitorObj visitor = new Visitor("John Doe", "john@com.org", (PaymentInfo) paymentInfo);
        String title = "This is my title";
        String text = "This is my text";
        String toSend = "{\"vid\": \""+visitor.getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        assertEquals(400, ParkResource.createNoteWithPark(toSend, pid).getStatus());
    }

    @Test
    void createNoteWithParkValid() {
        String pid = buildPark();
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
        String pid = buildPark();
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
        String pid = buildPark();
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[]}]";
        assertEquals(expected, ParkResource.notesByPark(pid).getEntity().toString());
    }

    @Test
    void getNoteWithParkBadRequest() {
        assertEquals(400, ParkResource.getNoteWithPark("non_exist", "uncoorolated").getStatus());
    }

    @Test
    void getNoteWithParkNotFound() {
        String pid = buildPark();
        assertEquals(400, ParkResource.getNoteWithPark(pid, "non_real_nid").getStatus());
    }



    @Test
    void getNoteWithParkOK() {
        String pid = buildPark();
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
        String pid = buildPark();
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
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoRegion(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(204, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoAddr(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoPhone(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoWeb(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoGeo(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\"}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkNoPaymentInfo(){
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}}";
        assertEquals(400, ParkResource.updatePark(toSend, pid).getStatus());
    }

    @Test
    void updateParkInvalidChargeData() {
        double [] localMPrices = mPrices;

        for(int i = 0; i < localMPrices.length; i++)
            localMPrices[i] = localMPrices[1]*-1;
        String localCleanMArray = "[" + localMPrices[0] + "," + localMPrices[1] + "]";
        String pid = buildPark();
        String toSend = "{\"location_info\": {\"name\": \""+newParkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+localCleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        Response result = ParkResource.updatePark(toSend, pid);
        assertEquals(400, result.getStatus());
    }


    


    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

}