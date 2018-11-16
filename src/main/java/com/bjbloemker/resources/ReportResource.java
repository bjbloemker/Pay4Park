package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.Error;
import com.bjbloemker.core.MemoryManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parkpay/reports")
@Produces("application/json")
public class ReportResource {
    private static Gson gson = new Gson();
    private static final String BEGINING_OF_TIME = "10000101";
    private static final String END_OF_TIME = "99991231";


    @GET
    public Response getReports(){
        JsonArray outputArray = new JsonArray();

        for(int i = 0; i < MemoryManager.reports.size(); i++){
            ReportObj report = MemoryManager.reports.get(i);
            JsonObject reportAsJsonObject = new JsonObject();
            reportAsJsonObject.addProperty("rid", report.getRid());
            reportAsJsonObject.addProperty("name", report.getTitle());

            outputArray.add(reportAsJsonObject);
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(outputArray)).build();
    }


    @GET
    @Path("/{rid}")
    public Response searchReports(@PathParam("rid") String rid, @QueryParam("start_date") String startDate, @QueryParam("end_date") String endDate) {
        ErrorObj error = new Error("http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation", "Your request data didn't pass validation", Response.Status.BAD_REQUEST.getStatusCode(), "/reports/" + rid );

        if(startDate == null || startDate.length() == 0){
            startDate = BEGINING_OF_TIME;
        }

        if(endDate == null || endDate.length() == 0){
            endDate = END_OF_TIME;
        }

        if(startDate.compareTo(endDate) > 0){
            ((Error) error).setDetail("Start date must be before end date");
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
        }

        if(startDate.length() !=8 || endDate.length() !=8 || !startDate.matches("[0-9]+") || !endDate.matches("[0-9]+")){
            ((Error) error).setDetail("Start date and end date must be 8 digits long");
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
        }

        if(!validateDate(startDate) || !validateDate(endDate)){
            ((Error) error).setDetail("Wrong date format");
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
        }

        JsonObject output = new JsonObject();
        if(rid.equals(""+MemoryManager.ADMISSION_REPORT_ID)){
            //admission report
            JsonArray detailByParks = new JsonArray();
            int sum = 0;
            for(ParkObj park : MemoryManager.parks){
                JsonObject individualPark = new JsonObject();
                int countForPark = getOrderCountByParkId(park.getPIDAsString(), startDate, endDate);
                sum += countForPark;
                individualPark.addProperty("pid", park.getPIDAsString());
                individualPark.addProperty("name", park.getLocationInfo().getName());
                individualPark.addProperty("admissions", countForPark);

                detailByParks.add(individualPark);
            }

            if(startDate.equals(BEGINING_OF_TIME))
                startDate ="";
            else
                startDate = convertSimpleDateToPrettyDate(startDate);

            if(endDate.equals(END_OF_TIME))
                endDate = "";
            else
                endDate = convertSimpleDateToPrettyDate(endDate);


            output.addProperty("rid", rid);
            output.addProperty("name", MemoryManager.ADMISSION_REPORT_TITLE);
            output.addProperty("start_date", startDate);
            output.addProperty("end_date", endDate);
            output.addProperty("total_admissions", sum);
            output.add("detail_by_park", detailByParks);

            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();


        }else if(rid.equals(""+MemoryManager.REVENUE_REPORT_ID)){
            //revenue report
            JsonArray detailByParks = new JsonArray();
            int sumOrdersCount = 0;
            double sumOrdersRevenue = 0;
            for(ParkObj park : MemoryManager.parks){
                JsonObject individualPark = new JsonObject();
                int countForPark = getOrderCountByParkId(park.getPIDAsString(), startDate, endDate);
                double revenueForPark = getRevenueByParkId(park.getPIDAsString(), startDate, endDate);
                sumOrdersCount += countForPark;
                sumOrdersRevenue += revenueForPark;
                individualPark.addProperty("pid", park.getPIDAsString());
                individualPark.addProperty("name", park.getLocationInfo().getName());
                individualPark.addProperty("orders", countForPark);
                individualPark.addProperty("revenue", revenueForPark);

                detailByParks.add(individualPark);
            }

            if(startDate.equals(BEGINING_OF_TIME))
                startDate ="";
            else
                startDate = convertSimpleDateToPrettyDate(startDate);

            if(endDate.equals(END_OF_TIME))
                endDate = "";
            else
                endDate = convertSimpleDateToPrettyDate(endDate);
            output.addProperty("rid", rid);
            output.addProperty("name", MemoryManager.REVENUE_REPORT_TITLE);
            output.addProperty("start_date", startDate);
            output.addProperty("end_date", endDate);
            output.addProperty("total_orders", sumOrdersCount);
            output.addProperty("total_revenue", sumOrdersRevenue);
            output.add("detail_by_park", detailByParks);

            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();

        }

        ((Error) error).setDetail("No report with provided rid");
        return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
    }


    private String convertSimpleDateToPrettyDate(String simpleDate){
        String yyyy = simpleDate.substring(0,4);
        String mm = simpleDate.substring(4,6);
        String dd = simpleDate.substring(6,8);

        return yyyy + "-" + mm + "-" + dd;
    }

    private boolean validateDate(String simpleDate){
        final String MAX_MONTH = "12";
        String max_day = "31";
        String mm = simpleDate.substring(4,6);
        String dd = simpleDate.substring(6,8);
        if(mm.compareTo(MAX_MONTH) > 0)
            return false;

        if((mm.equals("01") || mm.equals("02") || mm.equals("03") || mm.equals("05") || mm.equals("07")
                || mm.equals("08") || mm.equals("10") || mm.equals("12")) && dd.compareTo(max_day) <= 0) {
            return true;
        }
        max_day = "30";
        if ((mm.equals("04") || mm.equals("06") || mm.equals("09") || mm.equals("11")) && dd.compareTo(max_day) <= 0 ){
            return true;
        }
        max_day = "29";
        if(dd.compareTo(max_day) <= 0){
            return true;
        }
        return false;

    }


    private static int getOrderCountByParkId(String pid, String startDate, String endDate){
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

    private static double getRevenueByParkId(String pid, String startDate, String endDate){
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
