package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.bjbloemker.exceptions.InvalidVehicleTypeException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralResources {
    private static Gson gson = new Gson();
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    public static Park findParkById(String id){
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            ParkObj park = MemoryManager.parks.get(i);
            if(park.getPIDAsString().equals(id)){
                return (Park) park;
            }
        }
        return null;
    }

    public static Note findNoteByParkId(String id){
        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
                if(note.getPIDAsString().equals(id)){
                return (Note) note;
            }
        }
        return null;
    }

    public static OrderObj findOrderById(String id) {
        for(int i = 0; i < MemoryManager.orders.size(); i++){
            OrderObj order = MemoryManager.orders.get(i);
            if(order.getOIDAsString().equals(id)){
                return order;
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

    public static VisitorObj findVisitorById(String id){
        for(int i = 0; i < MemoryManager.visitors.size(); i++){
            VisitorObj visitor = MemoryManager.visitors.get(i);
            if(visitor.getVIDAsString().equals(id)){
                return visitor;
            }
        }
        return null;
    }




    public static JsonElement parksWithoutProperty(List<ParkObj> parks,String property){
        JsonArray output = new JsonArray();

        for(int i = 0; i < parks.size(); i++){
            ParkObj currentPark = parks.get(i);
            JsonObject parkAsJsonObject = (JsonObject) gson.toJsonTree(currentPark);
            parkAsJsonObject.remove(property);
            output.add(parkAsJsonObject);
        }
        return output;
    }


    public static ArrayList<NoteObj> getAllNotesFromPark(String pid){
        ArrayList<NoteObj> notes = new ArrayList<>();

        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj currentNote = MemoryManager.notes.get(i);
            if(currentNote.getPIDAsString().equals(pid)){
                notes.add(currentNote);
            }
        }

        return notes;
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

    public static ArrayList<NoteObj> getAllNotesFromVisitor(String vid){
        ArrayList<NoteObj> notes = new ArrayList<>();


        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj currentNote = MemoryManager.notes.get(i);
            if(currentNote.getVIDAsString().equals(vid)){
                notes.add(currentNote);
            }
        }
        return notes;
    }



    public static JsonElement simplifyOrders(List<OrderObj> orders){
        JsonArray output = new JsonArray();

        for(int i = 0; i < orders.size(); i++){
            OrderObj currentOrder = orders.get(i);
            JsonObject orderAsJsonObject = (JsonObject) gson.toJsonTree(currentOrder);
            JsonObject outputOrderAsJson = new JsonObject();

            String oid = orderAsJsonObject.get("oid").getAsString();
            String pid = orderAsJsonObject.get("pid").getAsString();
            String date = currentOrder.getDate();

            JsonObject vehicleAsJsonObject = orderAsJsonObject.get("vehicle").getAsJsonObject();
            VehicleObj vehicle = localJsonParser.JsonToVehicle(vehicleAsJsonObject);

            ParkObj park = findParkById(currentOrder.getPIDAsString());
            String vehicleType = vehicle.getType();

            outputOrderAsJson.addProperty("oid", oid);
            outputOrderAsJson.addProperty("pid", pid);
            outputOrderAsJson.addProperty("date", date);
            outputOrderAsJson.addProperty("type", vehicleType);
            outputOrderAsJson.addProperty("amount", calculateCost(vehicle, park));

            output.add(outputOrderAsJson);
        }

        return output;
    }

    public static JsonElement superSimplifyOrders(List<OrderObj> orders){
        JsonArray output = new JsonArray();

        for(int i = 0; i < orders.size(); i++){
            OrderObj currentOrder = orders.get(i);
            JsonObject outputOrderAsJson = new JsonObject();

            String oid = currentOrder.getOIDAsString();
            String pid = currentOrder.getPIDAsString();
            String date = currentOrder.getDate();

            outputOrderAsJson.addProperty("oid", oid);
            outputOrderAsJson.addProperty("pid", pid);
            outputOrderAsJson.addProperty("date", date);

            output.add(outputOrderAsJson);
        }

        return output;
    }

    public static JsonElement superSimplifyNotes(List<NoteObj> notes){
        JsonArray output = new JsonArray();

        for(int i = 0; i < notes.size(); i++){
            NoteObj currentNote = notes.get(i);
            JsonObject outputOrderAsJson = new JsonObject();

            String nid = currentNote.getNIDAsString();
            String pid = currentNote.getPIDAsString();
            String date = currentNote.getDate();
            String title = currentNote.getTitle();

            outputOrderAsJson.addProperty("nid", nid);
            outputOrderAsJson.addProperty("pid", pid);
            outputOrderAsJson.addProperty("date", date);
            outputOrderAsJson.addProperty("title", title);

            output.add(outputOrderAsJson);
        }

        return output;
    }



    public static JsonElement visitorsWithoutProperty(List<VisitorObj> visitors, String property){
        JsonArray output = new JsonArray();

        for(int i =0; i < visitors.size(); i++){
            VisitorObj currentVisitor = visitors.get(i);
            JsonObject visitorAsJsonObject = (JsonObject) gson.toJsonTree(currentVisitor);
            visitorAsJsonObject.remove(property);

            output.add(visitorAsJsonObject);
        }
        return output;
    }




    private static double calculateCost(VehicleObj vehicle, ParkObj park){
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
            throw new InvalidVehicleTypeException("Vehcile type must by motorcycle, RV, or Car");
        } catch (InvalidVehicleTypeException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
