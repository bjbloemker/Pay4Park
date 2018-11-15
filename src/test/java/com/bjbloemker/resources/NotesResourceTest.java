package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.MemoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class NotesResourceTest {

    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    private static ParkResource ParkResource = new ParkResource();
    private static OrderResource OrderResource = new OrderResource();
    private static VisitorsResource VisitorsResource = new VisitorsResource();
    private static NotesResource NotesResource = new NotesResource();

    private  String parkName = "Park Name";
    private static String region = "Northern Region";
    private static String address = "1523 Grant Rd Greenville, IL";
    private static String phone = "(513) 555-4020";
    private static String web = "https://www.dnr.illinois.gov/Parks/Pages/ParkName.aspx";
    private static float geoLat = 323.2f;
    private static float geoLng = -99.43f;

    private static double [] mPrices = {1,2};
    private static double [] cPrices = {3,4};
    private static double [] rPrices = {5,6};
    private static String cleanMArray = "[" + mPrices[0] + "," + mPrices[1] + "]";
    private static String cleanCArray = "[" + cPrices[0] + "," + cPrices[1] + "]";
    private static String cleanRArray = "[" + rPrices[0] + "," + rPrices[1] + "]";

    private static String cardNumber = "1111222233334444";
    private static String name = "Jane A. Doe";
    private static String expiration = "03/22";
    private static int zipCode = 60616;
    private static String email = "janeadoe@janesworld.com";

    private static String state = "IL";
    private static String plate = "ME8PL8";
    private static String type = "rv";


    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void searchNoteNoResults() {
        String pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is a possible title";
        String text = "This is some text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);

        Response result = NotesResource.searchNote("thiswillneverbefound");
        String expected = "[]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void searchNoteNullKey(){
        String pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is a possible title";
        String text = "This is some text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);

        NoteObj note = MemoryManager.notes.get(0);

        Response result = NotesResource.searchNote(null);
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[{\"nid\":\""+note.getNIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+note.getTitle()+"\"}]}]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void searchNoteOK() {
        String pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String title = "This is a possible title";
        String text = "This is some text";
        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);

        NoteObj note = MemoryManager.notes.get(0);

        Response result = NotesResource.searchNote("text");
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[{\"nid\":\""+note.getNIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+note.getTitle()+"\"}]}]";
        assertEquals(expected, result.getEntity());
    }


    @Test
    void getNoteDetail() {
    }

    @Test
    void getNoteDetailNotFound() {
        //I AM RIGHT HERE
    }


    private String buildPark(){
        String json = "{\"location_info\": {\"name\": \""+parkName+"\", \"region\": \""+region+"\", \"address\": \""+address+"\", \"phone\": \""+phone+"\", \"web\": \""+web+"\", \"geo\": {\"lat\": "+geoLat+", \"lng\": "+geoLng+"}}, \"payment_info\": {\"motorcycle\": "+cleanMArray+", \"car\": "+cleanCArray+", \"rv\": "+cleanRArray+"}}";
        ParkResource.createPark(json);
        int lastPos = MemoryManager.parks.size()-1;
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