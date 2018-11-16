package com.bjbloemker.resources;

import com.bjbloemker.api.OrderObj;
import com.bjbloemker.core.MemoryManager;
import com.google.gson.Gson;

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




}
