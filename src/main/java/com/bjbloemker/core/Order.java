package com.bjbloemker.core;

import com.bjbloemker.api.OrderObj;

public class Order extends OrderObj{

    public Order(String pid, Vehicle vehicle, Visitor visitor){
        super(pid, vehicle, visitor);
    }

    @Override
    public String toString() {
        return "OrderObj{" +
                "oid='" + oid + '\'' +
                ", vehicle=" + vehicle +
                ", visitor=" + visitor +
                '}';
    }


}
