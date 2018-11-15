package com.bjbloemker.resources;

import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.MemoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class VisitorsResourceTest extends DataForTesting{

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void getVisitorDetail() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);
        VisitorObj visitor = order.getVisitor();

        Response result = VisitorsResource.getVisitorDetail(visitor.getVIDAsString());

        String expected = "{\"vid\":\""+visitor.getVIDAsString()+"\",\"visitor\":{\"name\":\""+visitor.getName()+"\",\"email\":\""+visitor.getEmail()+"\"},\"orders\":[{\"oid\":\""+order.getOIDAsString()+"\",\"pid\":\""+order.getOIDAsString()+"\",\"date\":\""+order.getDate()+"\"}],\"notes\":[]}";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void getVisitorDetailNotFound() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);

        Response result = VisitorsResource.getVisitorDetail("fake_identifier");

        assertEquals(404, result.getStatus());
    }


    @Test
    void searchVisitorsNoResults() {
        buildParkAndSetupOrder();
        Response result = VisitorsResource.searchVisitors("nobodywillfindthis");

        assertEquals("[]", result.getEntity());
    }

    @Test
    void searchVisitorsWithKey() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);
        Response result = VisitorsResource.searchVisitors("Jane");

        VisitorObj visitor = order.getVisitor();
        String expected = "[{\"vid\":\""+visitor.getVIDAsString()+"\",\"name\":\""+visitor.getName()+"\",\"email\":\""+visitor.getEmail()+"\"}]";
        assertEquals(expected, result.getEntity());

    }


    @Test
    void searchVisitorsWithNullKey() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);
        Response result = VisitorsResource.searchVisitors(null);

        VisitorObj visitor = order.getVisitor();
        String expected = "[{\"vid\":\""+visitor.getVIDAsString()+"\",\"name\":\""+visitor.getName()+"\",\"email\":\""+visitor.getEmail()+"\"}]";
        assertEquals(expected, result.getEntity());

    }

    private void buildParkAndSetupOrder(){
        pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        OrderResource.createOrder(toSendToOrder);
    }

    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

}