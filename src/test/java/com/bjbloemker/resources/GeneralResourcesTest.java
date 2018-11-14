package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.google.gson.JsonElement;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sun.java2d.loops.FillRect;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GeneralResourcesTest {
    static ArrayList<ParkObj> parks;
    static ArrayList<NoteObj> notes;
    static ArrayList<OrderObj> orders;
    static ArrayList<VisitorObj> visitors;
    static ArrayList<VehicleObj> vehicles;


    @BeforeAll
    public static void initialize(){
        parks = new ArrayList<>();
        notes = new ArrayList<>();
        orders = new ArrayList<>();
        visitors = new ArrayList<>();
        vehicles = new ArrayList<>();


        GeoCordsObj geoCords = new GeoCords((float) 22.25, (float) -90.55);
        LocationInfoObj locationInfo = new LocationInfo("Park1" , "Region1", "1111 Street Ln Chicago, IL", "(555) 123-4567", "www.park1.gov", geoCords);
        double [] mPrice = {1,2};
        double [] cPrice = {3,4};
        double [] rPrice = {5,6};
        ChargeInfoObj chargeInfo1 = new ChargeInfo(mPrice, cPrice, rPrice);
        ParkObj park1 = new Park(locationInfo, chargeInfo1);

        GeoCordsObj geoCords2 = new GeoCords((float) 98.88, (float) -4.55);
        LocationInfoObj locationInfo2 = new LocationInfo("Park2" , "Region2", "2222 Lane St Cincinnati, OH", "(666) 321-7654", "www.park2.com", geoCords2);
        double [] mPrice2 = {1.5,2.5};
        double [] cPrice2 = {3.5,4.5};
        double [] rPrice2 = {5.5,6.5};
        ChargeInfoObj chargeInfo2 = new ChargeInfo(mPrice2, cPrice2, rPrice2);

        ParkObj park2 = new Park(locationInfo2, chargeInfo2);

        parks.add(park1);
        parks.add(park2);


        MemoryManager.parks.add(park1);
        MemoryManager.parks.add(park2);


        //create notes for park1
        //Payment info
        PaymentInfoObj paymentInfo1 = new PaymentInfo("1111222233334444", "John A Doe", "10/21", 60616);
        PaymentInfoObj paymentInfo2 = new PaymentInfo("4444333322221111", "Jane B Smith", "08/23", 40059);

        //Visitor
        VisitorObj visitor1 = new Visitor("John Doe", "jon.doe@john.com", (PaymentInfo) paymentInfo1);
        visitors.add(visitor1);
        MemoryManager.requestAddToVisitor(visitor1);

        VisitorObj visitor2 = new Visitor("Jane Doe", "jane.b.smith@gmail.com", (PaymentInfo) paymentInfo2);
        visitors.add(visitor2);
        MemoryManager.requestAddToVisitor(visitor2);

        //Notes
        NoteObj note1 = new Note("Title Text", "Content content content", park1.getPIDAsString(), visitor1.getVIDAsString());
        notes.add(note1);
        MemoryManager.notes.add(note1);

        NoteObj note2 = new Note("Title Text 2", "Content content content 2", park2.getPIDAsString(), visitor1.getVIDAsString());
        notes.add(note2);
        MemoryManager.notes.add(note2);

        NoteObj note3 = new Note("Title Text 3", "Content content content 3", park2.getPIDAsString(), visitor2.getVIDAsString());
        notes.add(note3);
        MemoryManager.notes.add(note3);

        //vehicle
        VehicleObj vehicle1 = new Vehicle("IL", "Pl84ME", "car");
        vehicles.add(vehicle1);

        VehicleObj vehicle2 = new Vehicle("KY", "JANESRV", "rv");
        vehicles.add(vehicle2);

        //order
        OrderObj order1 = new Order(park2.getPIDAsString(),vehicle1, visitor1);
        orders.add(order1);
        MemoryManager.orders.add(order1);

        OrderObj order2 = new Order(park2.getPIDAsString(),vehicle2, visitor2);
        orders.add(order2);
        MemoryManager.orders.add(order2);

    }

    @Test
    void findParkById() {
        String id1 = parks.get(0).getPIDAsString();
        ParkObj park1 = parks.get(0);
        assertEquals(park1, GeneralResources.findParkById(id1));
    }
    @Test
    void findParkByIdNull() {
        assertNull(GeneralResources.findParkById("fake_idetifier"));
    }


    @Test
    void findNoteByParkId() {
        NoteObj localNote = notes.get(0);
        NoteObj toCompare = GeneralResources.findNoteByParkId(parks.get(0).getPIDAsString());
        assertEquals(localNote, toCompare);
    }

    @Test
    void findNoteByParkIdNull() {
        assertNull(GeneralResources.findNoteByParkId("fake_idetifier"));
    }

    @Test
    void findOrderById() {
        OrderObj order = orders.get(0);
        String id  = orders.get(0).getOIDAsString();
        assertEquals(order, GeneralResources.findOrderById(id));
    }

    @Test
    void findOrderByIdNull() {
        assertNull(GeneralResources.findOrderById("fake_identifier"));
    }


    @Test
    void findNoteByNoteId() {
        NoteObj note = notes.get(0);
        String id = notes.get(0).getNIDAsString();
        assertEquals(note, GeneralResources.findNoteByNoteId(id));
    }

    @Test
    void findNoteByNoteIdNull() {
        assertNull(GeneralResources.findNoteByNoteId("fake_idetifier"));
    }

    @Test
    void findVisitorById() {
        VisitorObj visitor = visitors.get(0);
        String id = visitors.get(0).getVIDAsString();
        assertEquals(visitor, GeneralResources.findVisitorById(id));
    }

    @Test
    void findVisitorByIdNull() {
        assertNull(GeneralResources.findVisitorById("fake_idetifier"));
    }

    @Test
    void findVisitorByEmail() {
        VisitorObj visitor = visitors.get(0);
        String email = visitors.get(0).getEmail();
        assertEquals(visitor, GeneralResources.findVisitorByEmail(email));
    }

    @Test
    void findVisitorByEmailNull() {
        assertNull(GeneralResources.findVisitorByEmail("fake_idetifier"));
    }

    @Test
    void parksWithoutProperty() {
        ParkObj park1 = parks.get(0);
        ParkObj park2 = parks.get(1);
        JsonElement element = GeneralResources.parksWithoutProperty(parks, "payment_info");
        String goal = "[{\"pid\":\""+park1.getPIDAsString()+"\",\"location_info\":{\"name\":\"Park1\",\"region\":\"Region1\",\"address\":\"1111 Street Ln Chicago, IL\",\"phone\":\"(555) 123-4567\",\"web\":\"www.park1.gov\",\"geo\":{\"lat\":22.25,\"lng\":-90.55}}}," +
                       "{\"pid\":\""+park2.getPIDAsString()+"\",\"location_info\":{\"name\":\"Park2\",\"region\":\"Region2\",\"address\":\"2222 Lane St Cincinnati, OH\",\"phone\":\"(666) 321-7654\",\"web\":\"www.park2.com\",\"geo\":{\"lat\":98.88,\"lng\":-4.55}}}]";
        assertEquals(goal, element.toString());
    }

    @Test
    void getAllNotesFromPark() {
        String id1 = parks.get(0).getPIDAsString();
        NoteObj note1 = notes.get(0);
        ArrayList<NoteObj> resultSet = GeneralResources.getAllNotesFromPark(id1);
        assertEquals(1, resultSet.size());
        assertEquals(note1, resultSet.get(0));

    }

    @Test
    void getAllOrdersFromVisitor() {
        VisitorObj visitor2 = visitors.get(1);
        String id2 = visitor2.getVIDAsString();
        ArrayList<OrderObj> ordersByCustomer = GeneralResources.getAllOrdersFromVisitor(id2);
        OrderObj order2 = orders.get(1);
        assertEquals(1, ordersByCustomer.size());
        assertEquals(order2, ordersByCustomer.get(0));
    }

    @Test
    void getAllNotesFromVisitor() {
        VisitorObj visitor1 = visitors.get(0);
        String vid1 = visitor1.getVIDAsString();

        ArrayList<NoteObj> localNotes = new ArrayList<>();

        for(int i = 0; i < notes.size(); i++){
            NoteObj currentNote = notes.get(i);
            if(currentNote.getVIDAsString().equals(vid1)){
                localNotes.add(currentNote);
            }
        }

        assertEquals(localNotes, GeneralResources.getAllNotesFromVisitor(vid1));
    }

    @Test
    void simplifyOrders() {
        OrderObj order1 = orders.get(0);
        VehicleObj vehicleOrder1 = order1.getVehicle();
        OrderObj order2 = orders.get(1);
        VehicleObj vehicleOrder2 = order2.getVehicle();

        String goal = "[{\"oid\":\""+order1.getOIDAsString()+"\",\"pid\":\""+order1.getPIDAsString()+"\",\"date\":\""+order1.getDate()+"\",\"type\":\""+vehicleOrder1.getType()+"\",\"amount\":4.5}," +
                       "{\"oid\":\""+order2.getOIDAsString()+"\",\"pid\":\""+order2.getPIDAsString()+"\",\"date\":\""+order2.getDate()+"\",\"type\":\""+vehicleOrder2.getType()+"\",\"amount\":6.5}]";

        assertEquals(goal, GeneralResources.simplifyOrders(orders).toString());
    }

    @Test
    void superSimplifyOrders() {
        OrderObj order1 = orders.get(0);
        OrderObj order2 = orders.get(1);

        String goal = "[{\"oid\":\""+order1.getOIDAsString()+"\",\"pid\":\""+order1.getPIDAsString()+"\",\"date\":\""+order1.getDate()+"\"}," +
                       "{\"oid\":\""+order2.getOIDAsString()+"\",\"pid\":\""+order2.getPIDAsString()+"\",\"date\":\""+order2.getDate()+"\"}]";

        assertEquals(goal, GeneralResources.superSimplifyOrders(orders).toString());
    }

    @Test
    void superSimplifyNotes() {
        NoteObj note1 = notes.get(0);
        NoteObj note2 = notes.get(1);
        NoteObj note3 = notes.get(2);

        String goal =  "[{\"nid\":\""+note1.getNIDAsString()+"\",\"pid\":\""+note1.getPIDAsString()+"\",\"date\":\""+note1.getDate()+"\",\"title\":\""+note1.getTitle()+"\"}," +
                        "{\"nid\":\""+note2.getNIDAsString()+"\",\"pid\":\""+note2.getPIDAsString()+"\",\"date\":\""+note2.getDate()+"\",\"title\":\""+note2.getTitle()+"\"}," +
                        "{\"nid\":\""+note3.getNIDAsString()+"\",\"pid\":\""+note3.getPIDAsString()+"\",\"date\":\""+note3.getDate()+"\",\"title\":\""+note3.getTitle()+"\"}]";
        assertEquals(goal, GeneralResources.superSimplifyNotes(notes).toString());
    }

    @Test
    void simplifyVisitors() {
        VisitorObj visitor1 = visitors.get(0);
        VisitorObj visitor2 = visitors.get(1);
        String goal = "[{\"vid\":\""+visitor1.getVIDAsString()+"\",\"name\":\""+visitor1.getName()+"\",\"email\":\""+visitor1.getEmail()+"\"}," +
                "{\"vid\":\""+visitor2.getVIDAsString()+"\",\"name\":\""+visitor2.getName()+"\",\"email\":\""+visitor2.getEmail()+"\"}]";
        assertEquals(goal, GeneralResources.simplifyVisitors(visitors).toString());

    }

    @Test
    void visitorsWithoutProperty() {
        VisitorObj visitor1 = visitors.get(0);
        VisitorObj visitor2 = visitors.get(1);

        String goal = "[{\"vid\":\""+visitor1.getVIDAsString()+"\",\"name\":\""+visitor1.getName()+"\",\"email\":\""+visitor1.getEmail()+"\"}," +
                       "{\"vid\":\""+visitor2.getVIDAsString()+"\",\"name\":\""+visitor2.getName()+"\",\"email\":\""+visitor2.getEmail()+"\"}]";

        assertEquals(goal, GeneralResources.visitorsWithoutProperty(visitors, "payment_info").toString());

    }

    @Test
    void calculateCost() {
        ParkObj park1 = parks.get(0);
        VehicleObj vehicle1 = vehicles.get(0);
        assertEquals(3, GeneralResources.calculateCost(vehicle1, park1));
    }

    @AfterAll
    static void cleanUp(){
        try {
            for (Iterator<ParkObj> iterator = MemoryManager.parks.iterator(); iterator.hasNext(); ) {
                iterator.remove();
            }
        }catch(IllegalStateException e){}
    }

}