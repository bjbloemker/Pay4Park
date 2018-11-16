package com.bjbloemker.resources;

import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.MemoryManager;

public abstract class ReportServices {
    protected String convertSimpleDateToPrettyDate(String simpleDate){
        String yyyy = simpleDate.substring(0,4);
        String mm = simpleDate.substring(4,6);
        String dd = simpleDate.substring(6,8);

        return yyyy + "-" + mm + "-" + dd;
    }




    protected static int getOrderCountByParkId(String pid, String startDate, String endDate){
        ParkObj park = GeneralServices.findParkById(pid);
        if(park == null)
            return -1;
        int count = 0;
        for(OrderObj order : MemoryManager.orders){
            String orderDate = order.getDate().replace("-","");//formating the same
            if(startDate.compareTo(orderDate) <= 0 && endDate.compareTo(orderDate) > 0 && order.getPIDAsString().equals(pid)){
                count++;
            }
        }
        return count;
    }

    protected static double getRevenueByParkId(String pid, String startDate, String endDate){
        ParkObj park = GeneralServices.findParkById(pid);
        if(park == null)
            return -1;
        double sum = 0;
        for(OrderObj order : MemoryManager.orders){
            String orderDate = order.getDate().replace("-","");//formating the same
            if(startDate.compareTo(orderDate) <= 0 && endDate.compareTo(orderDate) > 0 && order.getPIDAsString().equals(pid)){
                sum += GeneralServices.calculateCost(order.getVehicle(), park);
            }
        }
        return sum;
    }
}
