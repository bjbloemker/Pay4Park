package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.bjbloemker.exceptions.InvalidVehicleTypeException;
import com.bjbloemker.exceptions.NullVehicleException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralServices {
    private static Gson gson = new Gson();
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    private static final String BEGINING_OF_TIME = "10000101";
    private static final String END_OF_TIME = "99991231";

    //logic in this class is used by 2 or more resources

    public static Park findParkById(String id){
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            ParkObj park = MemoryManager.parks.get(i);
            if(park.getPIDAsString().equals(id)){
                return (Park) park;
            }
        }
        return null;
    }

    public static NoteObj findNoteByNoteId(String id){
        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
            if(note.getNIDAsString().equals(id)){
                return note;
            }
        }
        return null;
    }

    public static VisitorObj findVisitorByEmail(String email){
        for(int i = 0; i < MemoryManager.visitors.size(); i++){
            VisitorObj visitor = MemoryManager.visitors.get(i);
            if(visitor.getEmail().equals(email)){
                return visitor;
            }
        }
        return null;
    }

    public static ArrayList<OrderObj> getAllOrdersFromVisitor(String vid){
        ArrayList<OrderObj> orders = new ArrayList<>();

        for(int i = 0; i < MemoryManager.orders.size(); i++){
            OrderObj currentOrder = MemoryManager.orders.get(i);
            VisitorObj visitorFromOrder = currentOrder.getVisitor();
            if(visitorFromOrder.getVIDAsString().equals(vid)){
                orders.add(currentOrder);
            }
        }

        return orders;
    }


    public static double calculateCost(VehicleObj vehicle, ParkObj park){
        String vehicleState = vehicle.getState();
        String vehicleType = vehicle.getType();
        String parkAddr = park.getLocationInfo().getAddress();
        int positionInArray = 1;//0 for in state, 1 for out of state

        if(parkAddr.contains(vehicleState)){
            positionInArray = 0;
        }

        ChargeInfoObj chargeInfo = park.getPaymentInfo();
        if(vehicleType.equalsIgnoreCase("motorcycle")){
            return chargeInfo.getMotorcycle()[positionInArray];
        }
        if(vehicleType.equalsIgnoreCase("car")){
            return chargeInfo.getCar()[positionInArray];
        }
        if(vehicleType.equalsIgnoreCase("rv")){
            return chargeInfo.getRv()[positionInArray];
        }

        try {
            throw new InvalidVehicleTypeException("Vehicle type must by motorcycle, RV, or Car");
        } catch (InvalidVehicleTypeException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static ArrayList<ParkObj> searchParks(String key){
        ArrayList<ParkObj> results = new ArrayList<>();

        for(int i =0; i< MemoryManager.parks.size(); i++){
            ParkObj park = MemoryManager.parks.get(i);
            LocationInfo locationInfo = (LocationInfo) park.getLocationInfo();
            String parkName = locationInfo.getName().toUpperCase();
            String parkAddr = locationInfo.getAddress().toUpperCase();
            String parkPhone = locationInfo.getPhone().toUpperCase();
            String parkWeb = locationInfo.getWeb().toUpperCase();
            String parkReg = locationInfo.getRegion().toUpperCase();

            GeoCordsObj geoCords = locationInfo.getGeo();
            String parkLat = (geoCords.getLat() + "").toUpperCase();
            String parkLng = (geoCords.getLng() + "").toUpperCase();

            if(parkName.contains(key) ||
                    parkAddr.contains(key) ||
                    parkPhone.contains(key) ||
                    parkWeb.contains(key) ||
                    parkReg.contains(key) ||
                    parkLat.contains(key) ||
                    parkLng.contains(key))
                results.add(park);
        }
        return results;
    }

    public static ArrayList<NoteObj> searchNotes(String key, String startDate, String endDate){
        ArrayList<NoteObj> results = new ArrayList<>();


        key = key.toUpperCase();
        for (int i = 0; i < MemoryManager.notes.size(); i++) {
            NoteObj note = MemoryManager.notes.get(i);
            String title = note.getTitle().toUpperCase();
            String content = note.getText().toUpperCase();
            String date = note.getDate().toUpperCase();

            if ((title.contains(key) || content.contains(key) || date.contains(key))){
                if(startDate == null && endDate == null)
                    results.add(note);
                else{
                    String cleanDate = date.replace("-", "");
                    if(startDate==null)
                        startDate = BEGINING_OF_TIME;
                    if(endDate == null)
                        endDate = END_OF_TIME;

                    if(startDate.compareTo(cleanDate) <= 0 && endDate.compareTo(cleanDate) > 0){
                        results.add(note);
                    }
                }
            }
        }
        return results;

    }

    public static ArrayList<OrderObj> searchOrder(String key, String startDate, String endDate){
        ArrayList<OrderObj> results = new ArrayList<>();

        for(int i =0; i< MemoryManager.orders.size(); i++){
            OrderObj order = MemoryManager.orders.get(i);

            String OIDAsString = order.getOIDAsString().toUpperCase();
            String date = order.getDate();

            VehicleObj vehicleOfOrder = order.getVehicle();
            String state = vehicleOfOrder.getState().toUpperCase();
            String plate = vehicleOfOrder.getPlate().toUpperCase();
            String type = vehicleOfOrder.getType().toUpperCase();

            VisitorObj visitor = order.getVisitor();
            String VIDAsString = visitor.getVIDAsString().toUpperCase();
            String visitorName = visitor.getName().toUpperCase();
            String visitorEmail = visitor.getEmail().toUpperCase();

            PaymentInfoObj paymentInfoOfVisitorOfOrder = visitor.getPaymentInfo();
            String card = paymentInfoOfVisitorOfOrder.getCard().toUpperCase();
            String nameOnCard = paymentInfoOfVisitorOfOrder.getNameOnCard().toUpperCase();
            String expirationDate = paymentInfoOfVisitorOfOrder.getExpirationDate().toUpperCase();
            String zipCode = (paymentInfoOfVisitorOfOrder.getZip() + "").toUpperCase();

            if(OIDAsString.contains(key) ||
                    date.contains(key) ||
                    state.contains(key) ||
                    plate.contains(key) ||
                    type.contains(key) ||
                    VIDAsString.contains(key) ||
                    visitorName.contains(key) ||
                    visitorEmail.contains(key) ||
                    card.contains(key) ||
                    nameOnCard.contains(key) ||
                    expirationDate.contains(key) ||
                    zipCode.contains(key)){
                if(startDate == null && endDate == null)
                    results.add(order);
                else{
                    String cleanDate = date.replace("-", "") + "";
                    if(startDate==null)
                        startDate = BEGINING_OF_TIME;
                    if(endDate == null)
                        endDate = END_OF_TIME;
                    if(startDate.compareTo(cleanDate) <= 0 && endDate.compareTo(cleanDate) > 0){
                        results.add(order);
                    }
                }
            }

        }

        return results;
    }

    public static ArrayList<VisitorObj> searchVisitors(String key){
        ArrayList<VisitorObj> results = new ArrayList<>();

        for(int i =0; i < MemoryManager.visitors.size(); i++){
            VisitorObj visitor = MemoryManager.visitors.get(i);


            String VIDAsString = visitor.getVIDAsString().toUpperCase();
            String visitorName = visitor.getName().toUpperCase();
            String visitorEmail = visitor.getEmail().toUpperCase();


            PaymentInfoObj paymentInfoOfVisitorOfOrder = visitor.getPaymentInfo();
            String card = paymentInfoOfVisitorOfOrder.getCard().toUpperCase();
            String nameOnCard = paymentInfoOfVisitorOfOrder.getNameOnCard().toUpperCase();
            String expirationDate = paymentInfoOfVisitorOfOrder.getExpirationDate().toUpperCase();
            String zipCode = (paymentInfoOfVisitorOfOrder.getZip() + "").toUpperCase();

            if(VIDAsString.contains(key) ||
                    visitorName.contains(key) ||
                    visitorEmail.contains(key) ||
                    card.contains(key) ||
                    nameOnCard.contains(key) ||
                    expirationDate.contains(key) ||
                    zipCode.contains(key)){
                results.add(visitor);
            }

        }

        return results;
    }

    public static  boolean validateDate(String simpleDate){
        final String MAX_MONTH = "12";
        String max_day = "31";
        String mm = simpleDate.substring(4,6);
        String dd = simpleDate.substring(6,8);
        if(mm.compareTo(MAX_MONTH) > 0)
            return false;

        if((mm.equals("01") || mm.equals("02") || mm.equals("03") || mm.equals("05") || mm.equals("07")
                || mm.equals("08") || mm.equals("10") || mm.equals("12")) && dd.compareTo(max_day) <= 0) {
            return true;
        }
        max_day = "30";
        if ((mm.equals("04") || mm.equals("06") || mm.equals("09") || mm.equals("11")) && dd.compareTo(max_day) <= 0 ){
            return true;
        }
        max_day = "29";
        if(dd.compareTo(max_day) <= 0){
            return true;
        }
        return false;

    }


}
