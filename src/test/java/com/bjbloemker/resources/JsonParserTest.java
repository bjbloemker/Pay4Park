package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.ChargeInfo;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.PaymentInfo;
import com.bjbloemker.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

    private static String parkName = "PARK nAmE";
    private static String region = "region 4";
    private static String address = "7654 Grand Vally Rd Chicago, IL";
    private static String phone = "(513) 555-9966";
    private static String web = "https://www.dnr.illinois.gov/Parks/Pages/MermetLake.aspx";
    private static float geoLat = 99.53f;
    private static float geoLng = -84.43f;

    private static double [] mPrices = {1,2};
    private static double [] cPrices = {3,4};
    private static double [] rPrices = {5,6};

    private static String state = "IL";
    private static String plate = "PL84ME";
    private static String type = "car";

    private static String cardNumber = "1111222233334444";
    private static String name = "Jane A. Doe";
    private static String expiration = "06/23";
    private static int zipCode = 60616;

    private static String email = "valid@email.com";



    @Test
    void jsonToLocation() throws NullPhoneException, NullGeoException, NullAddressException, NullNameException, NullWebException {

        JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
        LocationInfoObj result = JsonParser.JsonToLocation(json);

        //must test components individually because of Diff memory locations
        assertEquals(parkName, result.getName());
        assertEquals(region, result.getRegion());
        assertEquals(address, result.getAddress());
        assertEquals(phone, result.getPhone());
        assertEquals(web, result.getWeb());
        assertEquals(geoLat, result.getGeo().getLat());
        assertEquals(geoLng, result.getGeo().getLng());

    }

    @Test
    void jsonToLocationNullName(){
        assertThrows(NullNameException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
                    JsonParser.JsonToLocation(json);
                });
    }

    @Test
    void jsonToLocationNullRegion() throws NullPhoneException, NullGeoException, NullAddressException, NullNameException, NullWebException {

        JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
        LocationInfoObj result = JsonParser.JsonToLocation(json);

        assertEquals(parkName, result.getName());
        assertEquals("", result.getRegion());
        assertEquals(address, result.getAddress());
        assertEquals(phone, result.getPhone());
        assertEquals(web, result.getWeb());
        assertEquals(geoLat, result.getGeo().getLat());
        assertEquals(geoLng, result.getGeo().getLng());
    }

    @Test
    void jsonToLocationNullAddr(){
        assertThrows(NullAddressException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"region\":\""+region+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
                    JsonParser.JsonToLocation(json);
                });
    }

    @Test
    void jsonToLocationNullPhone(){
        assertThrows(NullPhoneException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"web\":\""+web+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
                    JsonParser.JsonToLocation(json);
                });
    }

    @Test
    void jsonToLocationNullWeb(){
        assertThrows(NullWebException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"geo\":{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}}");
                    JsonParser.JsonToLocation(json);
                });
    }

    @Test
    void jsonToLocationNullGeo(){
        assertThrows(NullGeoException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"name\":\""+ parkName +"\",\"region\":\""+region+"\",\"address\":\""+address+"\",\"phone\":\""+phone+"\",\"web\":\""+web+"\"}");
                    JsonParser.JsonToLocation(json);
                });
    }


    @Test
    void jsonToGeoCords() throws NullGeoException {
        JsonObject json = (JsonObject) parser.parse("{\"lat\":"+geoLat+",\"lng\":"+geoLng+"}");
        GeoCordsObj result = JsonParser.JsonToGeoCords(json);

        assertEquals(geoLat, result.getLat());
        assertEquals(geoLng, result.getLng());
    }

    @Test
    void jsonToGeoCordsNull1()  {
        assertThrows(NullGeoException.class,
                ()->{
                    JsonObject json = (JsonObject) parser.parse("{\"lat\":"+geoLat+"}");
                    JsonParser.JsonToGeoCords(json);
                });
    }


    @Test
    void jsonToChargeInfo() throws InvalidPriceException, NullChargeInfoException {
        String cleanMArray = "[" + mPrices[0] + "," + mPrices[1] + "]";
        String cleanCArray = "[" + cPrices[0] + "," + cPrices[1] + "]";
        String cleanRArray = "[" + rPrices[0] + "," + rPrices[1] + "]";

        JsonObject json = (JsonObject) parser.parse("{\"motorcycle\":"+cleanMArray+",\"car\":"+cleanCArray+",\"rv\":"+cleanRArray+"}");
        ChargeInfo result = JsonParser.JsonToChargeInfo(json);

        double [] localMPrices = result.getMotorcycle();
        double [] localCPrices = result.getCar();
        double [] localRPrices = result.getRv();

        for(int i = 0; i < localMPrices.length; i++){
            assertEquals(mPrices[i], localMPrices[i]);
        }
        for(int i = 0; i < localCPrices.length; i++){
            assertEquals(cPrices[i], localCPrices[i]);
        }
        for(int i = 0; i < localRPrices.length; i++){
            assertEquals(rPrices[i], localRPrices[i]);
        }
    }

    @Test
    void jsonToChargeInfoBadPrices() throws InvalidPriceException {

        for(int i = 0; i < mPrices.length; i++){
            mPrices[i] = mPrices[i]*-1;
        }

        String cleanMArray = "[" + mPrices[0] + "," + mPrices[1] + "]";
        String cleanCArray = "[" + cPrices[0] + "," + cPrices[1] + "]";
        String cleanRArray = "[" + rPrices[0] + "," + rPrices[1] + "]";

        for(int i = 0; i < mPrices.length; i++){
            assertThrows(InvalidPriceException.class, ()->{
                JsonObject json = (JsonObject) parser.parse("{\"motorcycle\":"+cleanMArray+",\"car\":"+cleanCArray+",\"rv\":"+cleanRArray+"}");
                JsonParser.JsonToChargeInfo(json);
            });
        }

        mPrices[0] = 1;
        mPrices[1] = 2;

        //car
        for(int i = 0; i < cPrices.length; i++){
            cPrices[i] = cPrices[i]*-1;
        }

        String cleanMArray2 = "[" + mPrices[0] + "," + mPrices[1] + "]";
        String cleanCArray2 = "[" + cPrices[0] + "," + cPrices[1] + "]";
        String cleanRArray2 = "[" + rPrices[0] + "," + rPrices[1] + "]";

        for(int i = 0; i < cPrices.length; i++){
            assertThrows(InvalidPriceException.class, ()->{
                JsonObject json = (JsonObject) parser.parse("{\"motorcycle\":"+cleanMArray2+",\"car\":"+cleanCArray2+",\"rv\":"+cleanRArray2+"}");
                JsonParser.JsonToChargeInfo(json);
            });
        }

        cPrices[0] = 3;
        cPrices[1] = 4;

        //rv
        for(int i = 0; i < rPrices.length; i++){
            rPrices[i] = rPrices[i]*-1;
        }

        String cleanMArray3 = "[" + mPrices[0] + "," + mPrices[1] + "]";
        String cleanCArray3 = "[" + cPrices[0] + "," + cPrices[1] + "]";
        String cleanRArray3 = "[" + rPrices[0] + "," + rPrices[1] + "]";

        for(int i = 0; i < rPrices.length; i++){
            assertThrows(InvalidPriceException.class, ()->{
                JsonObject json = (JsonObject) parser.parse("{\"motorcycle\":"+cleanMArray3+",\"car\":"+cleanCArray3+",\"rv\":"+cleanRArray3+"}");
                JsonParser.JsonToChargeInfo(json);
            });
        }

        rPrices[0] = 5;
        rPrices[1] = 6;

    }

    @Test
    void jsonToVehicle() throws InvalidVehicleTypeException, NullVehicleException {
        JsonObject json = (JsonObject) parser.parse("{\"state\":\""+state+"\",\"plate\":\""+plate +"\",\"type\":\""+type +"\"}");
        VehicleObj result = JsonParser.JsonToVehicle(json);

        assertEquals(state, result.getState());
        assertEquals(plate, result.getPlate());
        assertEquals(type, result.getType());
    }


    @Test
    void jsonToVehicleBadVehicleCreationsMissingstate() {

        assertThrows(NullVehicleException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"plate\":\""+plate +"\",\"type\":\""+type +"\"}");
            JsonParser.JsonToVehicle(json);
        });
    }
    @Test
    void jsonToVehicleBadVehicleCreationsMissingPlate() {
        assertThrows(NullVehicleException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"state\":\""+state+"\",\"type\":\""+type +"\"}");
            JsonParser.JsonToVehicle(json);
        });
    }

    @Test
    void jsonToVehicleBadVehicleCreationsMissingType() {
        assertThrows(NullVehicleException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"state\":\""+state+"\",\"plate\":\""+plate +"\"}");
            JsonParser.JsonToVehicle(json);
        });

    }
    @Test
    void jsonToVehicleBadVehicleType() {
        type="INVALID";
        assertThrows(InvalidVehicleTypeException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"state\":\""+state+"\",\"plate\":\""+plate +"\",\"type\":\""+type +"\"}");
            VehicleObj result = JsonParser.JsonToVehicle(json);
        });
    }

    @Test
    void jsonToVisitor() throws NullEmailException, InvalidEmailException, InvalidCardException, NullCardException {
        JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
        VisitorObj result = JsonParser.JsonToVisitor(json);
        PaymentInfoObj resultPaymentInfo = result.getPaymentInfo();

        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(cardNumber, resultPaymentInfo.getCard());
        assertEquals(name, resultPaymentInfo.getNameOnCard());
        assertEquals(expiration, resultPaymentInfo.getExpirationDate());
        assertEquals(zipCode, resultPaymentInfo.getZip());
    }

    @Test
    void jsonToVisitorMissingEmail() {
        assertThrows(NullEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });
    }

    @Test
    void jsonToVisitorInvalidEmails() {
        String email1 = "invalid_email";
        assertThrows(InvalidEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email1+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });

        String email2 = "a@";
        assertThrows(InvalidEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email2+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });

        String email3 = "jane doe@gmail.com";
        assertThrows(InvalidEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email3+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });

        String email4 = "janedoe@gmailcom";
        assertThrows(InvalidEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email4+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });
        String email5 = "";
        assertThrows(NullEmailException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name\":\""+name+"\",\"email\":\""+email5+"\",\"payment_info\":{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}}");
            JsonParser.JsonToVisitor(json);
        });
    }



    @Test
    void jsonToPaymentInfo() throws NullCardException, InvalidCardException {
        JsonObject json = (JsonObject) parser.parse("{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}");
        PaymentInfo result = JsonParser.JsonToPaymentInfo(json);

        assertEquals(cardNumber, result.getCard());
        assertEquals(name, result.getNameOnCard());
        assertEquals(expiration, result.getExpirationDate());
        assertEquals(zipCode, result.getZip());

    }

    @Test
    void jsonToPaymentInfoMissingCard() {
        assertThrows(NullCardException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}");
            JsonParser.JsonToPaymentInfo(json);
        });
    }

    @Test
    void jsonToPaymentInfoMissingName() {
        assertThrows(NullCardException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"card\":\""+cardNumber+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}");
            JsonParser.JsonToPaymentInfo(json);
        });
    }

    @Test
    void jsonToPaymentInfoMissingExpiration() {
        assertThrows(NullCardException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"zip\":"+zipCode+"}");
            JsonParser.JsonToPaymentInfo(json);
        });
    }

    @Test
    void jsonToPaymentInfoMissingZip() {
        assertThrows(NullCardException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\"}");
            JsonParser.JsonToPaymentInfo(json);
        });
    }


    @Test
    void jsonToPaymentInfoInvalidCard() {
        cardNumber = "whatever this is, it is not 16 digits";
        assertThrows(InvalidCardException.class, ()->{
            JsonObject json = (JsonObject) parser.parse("{\"card\":\""+cardNumber+"\",\"name_on_card\":\""+name+"\",\"expiration_date\":\""+expiration+"\",\"zip\":"+zipCode+"}");
            JsonParser.JsonToPaymentInfo(json);
        });
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