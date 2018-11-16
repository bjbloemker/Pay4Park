package com.bjbloemker.resources;

import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.api.VehicleObj;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.exceptions.InvalidVehicleTypeException;
import com.bjbloemker.exceptions.NullVehicleException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public abstract class OrderServices {
    private static Gson gson = new Gson();
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    protected static OrderObj findOrderById(String id) {
        for(int i = 0; i < MemoryManager.orders.size(); i++){
            OrderObj order = MemoryManager.orders.get(i);
            if(order.getOIDAsString().equals(id)){
                return order;
            }
        }
        return null;
    }


    protected static JsonElement simplifyOrders(List<OrderObj> orders){
        JsonArray output = new JsonArray();

        for(int i = 0; i < orders.size(); i++){
            OrderObj currentOrder = orders.get(i);
            JsonObject orderAsJsonObject = (JsonObject) gson.toJsonTree(currentOrder);
            JsonObject outputOrderAsJson = new JsonObject();

            String oid = orderAsJsonObject.get("oid").getAsString();
            String pid = orderAsJsonObject.get("pid").getAsString();
            String date = currentOrder.getDate();

            JsonObject vehicleAsJsonObject = orderAsJsonObject.get("vehicle").getAsJsonObject();
            VehicleObj vehicle = null;
            try {
                vehicle = localJsonParser.JsonToVehicle(vehicleAsJsonObject);
            } catch (NullVehicleException e) {
                e.printStackTrace();
            } catch (InvalidVehicleTypeException e) {
                e.printStackTrace();
            }

            ParkObj park = GeneralServices.findParkById(currentOrder.getPIDAsString());
            String vehicleType = vehicle.getType();

            outputOrderAsJson.addProperty("oid", oid);
            outputOrderAsJson.addProperty("pid", pid);
            outputOrderAsJson.addProperty("date", date);
            outputOrderAsJson.addProperty("type", vehicleType);
            outputOrderAsJson.addProperty("amount", GeneralServices.calculateCost(vehicle, park));

            output.add(outputOrderAsJson);
        }

        return output;
    }

}
