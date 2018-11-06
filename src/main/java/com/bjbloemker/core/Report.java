package com.bjbloemker.core;

import java.util.ArrayList;

/*
"rid": 911,
    	"name": "Revenue report",
    	"start_date": "2018-08-01",
    	"end_date": "2018-08-31",
    	"total_orders": 2,
    	"total_revenue": 22.25,
    	"detail_by_park": [{
    			"pid": 123,
    			"name": "Apple River Canyon",
    			"orders": 1,
    			"revenue": 13.00
    		},
    		{
    			"pid": 124,
    			"name": "Castle Rock",
    			"orders": 1,
    			"revenue": 9.25
    		},
    		{
    			"pid": 131,
    			"name": "Mermet Lake",
    			"orders": 0,
    			"revenue": 0.00
    		}
    	]
 */
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
