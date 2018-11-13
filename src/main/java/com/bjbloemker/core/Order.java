package com.bjbloemker.core;

import com.bjbloemker.api.OrderObj;

public class Order extends OrderObj{

    public Order(String pid, Vehicle vehicle, Visitor visitor){
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
