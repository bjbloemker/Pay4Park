package com.bjbloemker.core;

import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.VehicleObj;
import com.bjbloemker.api.VisitorObj;

public class Order extends OrderObj{

    public Order(String pid, VehicleObj vehicle, VisitorObj visitor){
        super(pid, vehicle, visitor);
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", pid='" + pid + '\'' +
                ", date='" + date + '\'' +
                ", vehicle=" + vehicle +
                ", visitor=" + visitor +
                '}';
    }
}
