package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.MemoryManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public abstract class VisitorServices {
    private static Gson gson = new Gson();

    protected static VisitorObj findVisitorById(String id){
        for(int i = 0; i < MemoryManager.visitors.size(); i++){
            VisitorObj visitor = MemoryManager.visitors.get(i);
            if(visitor.getVIDAsString().equals(id)){
                return visitor;
            }
        }
        return null;
    }

    protected static ArrayList<NoteObj> getAllNotesFromVisitor(String vid){
        ArrayList<NoteObj> notes = new ArrayList<>();

        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj currentNote = MemoryManager.notes.get(i);
            if(currentNote.getVIDAsString().equals(vid)){
                notes.add(currentNote);
            }
        }
        return notes;
    }

    protected static JsonElement superSimplifyOrders(List<OrderObj> orders){
        JsonArray output = new JsonArray();

        for (OrderObj currentOrder : orders) {
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

    protected static JsonElement superSimplifyNotes(List<NoteObj> notes){
        JsonArray output = new JsonArray();

        for(NoteObj currentNote : notes){
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

    protected static JsonElement simplifyVisitors(List<VisitorObj> visitors){
        JsonArray output = new JsonArray();

        for (VisitorObj currentVisitor : visitors) {
            JsonObject outputVisitorAsJson = new JsonObject();

            String vid = currentVisitor.getVIDAsString();
            String name = currentVisitor.getName();
            String email = currentVisitor.getEmail();

            outputVisitorAsJson.addProperty("vid", vid);
            outputVisitorAsJson.addProperty("name", name);
            outputVisitorAsJson.addProperty("email", email);

            output.add(outputVisitorAsJson);
        }

        return output;
    }

    protected static JsonElement visitorsWithoutProperty(List<VisitorObj> visitors, String property){
        JsonArray output = new JsonArray();

        for (VisitorObj currentVisitor : visitors) {
            JsonObject visitorAsJsonObject = (JsonObject) gson.toJsonTree(currentVisitor);
            visitorAsJsonObject.remove(property);

            output.add(visitorAsJsonObject);
        }

        return output;
    }

}
