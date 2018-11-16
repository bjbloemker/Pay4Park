package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.Error;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/parkpay/search")
@Produces("application/json")
public class SearchResource {
    private static Gson gson = new Gson();

    @GET
    public Response search(@QueryParam("key") String key, @QueryParam("start_date") String startDate, @QueryParam("end_date") String endDate){

        if(key == null || key.equals(""))
            return Response.status(Response.Status.OK).build();

        ArrayList<ParkObj> parks = GeneralServices.searchParks(key);
        ArrayList<OrderObj> orders = GeneralServices.searchOrder(key, startDate, endDate);
        ArrayList<VisitorObj> visitors = GeneralServices.searchVisitors(key);
        ArrayList<NoteObj> notes = GeneralServices.searchNotes(key, startDate, endDate);

        if(parks.size() == 0 && orders.size() == 0 && visitors.size() == 0 && notes.size() == 0)
            return Response.status(Response.Status.OK).build();


        JsonObject searchResults = new JsonObject();

        if(parks.size() != 0){
            JsonElement output = GeneralServices.parksWithoutProperty(parks,"payment_info");
            searchResults.add("parks", output);
        }

        if(orders.size() != 0){
            JsonElement output = GeneralServices.simplifyOrders(orders);
            searchResults.add("orders",output);
        }

        if(visitors.size() != 0){
            JsonElement output = GeneralServices.simplifyVisitors(visitors);
            searchResults.add("visitors",output);
        }

        if(notes.size() != 0){
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            JsonElement output = parser.parse(gson.toJson(notes));
            searchResults.add("notes", output);

        }

        return Response.status(Response.Status.OK).entity(gson.toJson(searchResults)).build();

    }

}
