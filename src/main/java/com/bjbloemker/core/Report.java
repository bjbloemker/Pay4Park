package com.bjbloemker.core;

import java.util.ArrayList;

public class Report {
    String rid;
    String name;
    String startDate;
    String endDate;
    int totalOrders;
    double totalRevenue;
    ArrayList<Park> parks;

    public Report(String rid, String name, String startDate, String endDate, int totalOrders, double totalRevenue, ArrayList<Park> parks) {
        this.rid = rid;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.parks = parks;
    }
}
