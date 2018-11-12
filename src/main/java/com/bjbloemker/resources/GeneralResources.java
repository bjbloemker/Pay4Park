package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.Note;
import com.bjbloemker.core.Park;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralResources {
    private static Gson gson = new Gson();

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

    public static JsonElement parksWithoutProperty(String property){
        JsonArray output = new JsonArray();

        for(int i = 0; i < MemoryManager.parks.size(); i++){
            ParkObj currentPark = MemoryManager.parks.get(i);
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


}
