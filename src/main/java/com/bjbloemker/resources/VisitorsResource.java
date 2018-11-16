package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.MemoryManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parkpay/visitors")
@Produces("application/json")
public class VisitorsResource extends VisitorServices{
    private static Gson gson = new Gson();

    @GET
    @Path("/{vid}")
    public Response getVisitorDetail(@PathParam("vid") String id) {
        VisitorObj visitor = findVisitorById(id);
        if(visitor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        JsonObject output = new JsonObject();

        output.addProperty("vid", visitor.getVIDAsString());

        JsonObject innerVisitor = new JsonObject();
        innerVisitor.addProperty("name", visitor.getName());
        innerVisitor.addProperty("email", visitor.getEmail());

        output.add("visitor", innerVisitor);

        ArrayList<OrderObj> ordersByVisitor = GeneralServices.getAllOrdersFromVisitor(visitor.getVIDAsString());
        JsonElement toPutOrders  = superSimplifyOrders(ordersByVisitor);
        output.add("orders", toPutOrders);


        ArrayList<NoteObj> notesByVisitor = getAllNotesFromVisitor(visitor.getVIDAsString());
        JsonElement toPutNotes  = superSimplifyNotes(notesByVisitor);
        output.add("notes", toPutNotes);

        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }

    @GET
    public Response searchVisitors(@QueryParam("key") String key) {

        if(key == null || key.length() == 0){
            JsonElement output = visitorsWithoutProperty(MemoryManager.visitors, "payment_info");
            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();
        }

        key = key.toUpperCase();
        ArrayList<VisitorObj> results = GeneralServices.searchVisitors(key);

        JsonElement output = simplifyVisitors(results);
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.OK).entity(outputAsString).build();
    }
}
