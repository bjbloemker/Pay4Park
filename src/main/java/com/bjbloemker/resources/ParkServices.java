package com.bjbloemker.resources;

import com.bjbloemker.api.ParkObj;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public abstract class ParkServices {
    private static Gson gson = new Gson();

    protected static JsonElement parksWithoutProperty(List<ParkObj> parks, String property){
        JsonArray output = new JsonArray();

        for(ParkObj currentPark : parks){
            JsonObject parkAsJsonObject = (JsonObject) gson.toJsonTree(currentPark);
            parkAsJsonObject.remove(property);
            output.add(parkAsJsonObject);
        }

        return output;
    }
}
