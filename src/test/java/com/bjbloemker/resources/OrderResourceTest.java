package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class OrderResourceTest extends DataForTesting{

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void createOrder() {
        pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        Response result = OrderResource.createOrder(toSendToOrder);
        assertEquals(201, result.getStatus());
        String expected = "{\"oid\":\""+MemoryManager.orders.get(0).getOIDAsString()+"\"}";
        assertEquals(expected, result.getEntity().toString());
    }

    @Test
    void createParkNoPid(){
        String toSend = "{\"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }


    @Test
    void createParkNoVehicle(){
        String toSend = "{\"pid\": \""+pid+"\", \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkNoVisitor(){
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkMissingVehicleData(){
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkBadVehivleType(){
        String mistype = "ccar";
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+mistype+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkMissingCardInfo(){
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkBadCardInfo(){
        String badCardNumber = "4321";
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+badCardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkMissingVisitorEmail(){
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void createParkBadEmail(){
        String badEmail = "@.";
        String toSend = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+badEmail+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";
        assertEquals(400, OrderResource.createOrder(toSend).getStatus());
    }

    @Test
    void getOrderDetail() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);
        Response result = OrderResource.getOrderDetail(order.getOIDAsString());

        ParkObj park = MemoryManager.parks.get(0);
        VehicleObj vehicle = order.getVehicle();
        VisitorObj visitor = order.getVisitor();

        String expected = "{\"oid\":\""+order.getOIDAsString()+"\",\"pid\":\""+order.getPIDAsString()+"\",\"amount\":"+GeneralResources.calculateCost(vehicle, park)+",\"vid\":\""+visitor.getVIDAsString()+"\",\"date\":\""+order.getDate()
                +"\",\"vehicle\":{\"state\":\""+state+"\",\"plate\":\""+plate+"\",\"type\":\""+type+"\"},\"visitor\":{\"name\":\""+name+"\",\"email\":\""+email+"\",\"payment_info\":{\"card\":\""+((PaymentInfo)visitor.getPaymentInfo()).getHiddenCard()
                +"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}},\"payment_processing\":{\"card_transaction_id\":\""+order.getPaymentProcessing().getCardTransactionID()+"\",\"date_and_time\":\""
                +order.getPaymentProcessing().getDateAndTime()+"\"}}";
        assertEquals(expected, result.getEntity());
    }


    @Test
    void getOrderDetailNotFound() {
        buildParkAndSetupOrder();
        Response result = OrderResource.getOrderDetail("fake_idetifier");
        assertEquals(404, result.getStatus());

    }

    @Test
    void searchOrderNoResults() {
        buildParkAndSetupOrder();
        Response result = OrderResource.searchOrder("nobodywillfindthis");
        assertEquals("[]", result.getEntity());
    }

    @Test
    void searchOrderNullKey(){
        buildParkAndSetupOrder();
        Response result = OrderResource.searchOrder(null);
        OrderObj order = MemoryManager.orders.get(0);
        ParkObj park = MemoryManager.parks.get(0);
        VehicleObj vehicle  = order.getVehicle();

        String expected = "[{\"oid\":\""+order.getOIDAsString()+"\",\"pid\":\""+order.getPIDAsString()+"\",\"date\":\""+order.getDate()+"\",\"type\":\""+order.getVehicle().getType()+"\",\"amount\":"+GeneralResources.calculateCost(vehicle, park)+"}]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void searchOrderOK() {
        buildParkAndSetupOrder();
        OrderObj order = MemoryManager.orders.get(0);
        Response result = OrderResource.searchOrder("rv");

        ParkObj park = MemoryManager.parks.get(0);
        VehicleObj vehicle  = order.getVehicle();

        String expected = "[{\"oid\":\""+order.getOIDAsString()+"\",\"pid\":\""+order.getPIDAsString()+"\",\"date\":\""+order.getDate()+"\",\"type\":\""+order.getVehicle().getType()+"\",\"amount\":"+GeneralResources.calculateCost(vehicle, park)+"}]";
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