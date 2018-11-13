package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.Order;
import com.bjbloemker.core.Visitor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.tools.javah.Gen;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parkpay/visitors")
@Produces("application/json")
public class VisitorsResource {
    private static Gson gson = new Gson();

    @GET
    @Path("/{vid}")
    public Response getVisitorDetail(@PathParam("vid") String id) {
        VisitorObj visitor = GeneralResources.findVisitorById(id);
        if(visitor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        JsonObject output = new JsonObject();

        output.addProperty("vid", visitor.getVIDAsString());

        JsonObject innerVisitor = new JsonObject();
        innerVisitor.addProperty("name", visitor.getName());
        innerVisitor.addProperty("email", visitor.getEmail());

        output.add("visitor", innerVisitor);


        ArrayList<OrderObj> ordersByVisitor = GeneralResources.getAllOrdersFromVisitor(visitor.getVIDAsString());
        JsonElement toPutOrders  = GeneralResources.superSimplifyOrders(ordersByVisitor);
        output.add("orders", toPutOrders);


        ArrayList<NoteObj> notesByVisitor = GeneralResources.getAllNotesFromVisitor(visitor.getVIDAsString());
        JsonElement toPutNotes  = GeneralResources.superSimplifyNotes(notesByVisitor);
        output.add("notes", toPutNotes);

        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }



    @GET
    public Response searchVisitors(@QueryParam("key") String key) {

        if(key == null || key.length() == 0){
            JsonElement output = GeneralResources.visitorsWithoutProperty(MemoryManager.visitors, "payment_info");
            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();
        }

        key = key.toUpperCase();
        ArrayList<OrderObj> results = new ArrayList<>();

        for(int i =0; i< MemoryManager.orders.size(); i++){
            OrderObj order = (Order) MemoryManager.orders.get(i);

            String OIDAsString = order.getOIDAsString().toUpperCase();
            String PIDAsString = order.getPIDAsString().toUpperCase();

            VehicleObj vehicleOfOrder = order.getVehicle();
            String state = vehicleOfOrder.getState().toUpperCase();
            String plate = vehicleOfOrder.getPlate().toUpperCase();
            String type = vehicleOfOrder.getType().toUpperCase();

            VisitorObj visitor = order.getVisitor();
            String VIDAsString = visitor.getVIDAsString().toUpperCase();
            String visitorName = visitor.getName().toUpperCase();

            PaymentInfoObj paymentInfoOfVisitorOfOrder = visitor.getPaymentInfo();
            String card = paymentInfoOfVisitorOfOrder.getCard().toUpperCase();
            String nameOnCard = paymentInfoOfVisitorOfOrder.getNameOnCard().toUpperCase();
            String expirationDate = paymentInfoOfVisitorOfOrder.getExpirationDate().toUpperCase();
            String zipCode = (paymentInfoOfVisitorOfOrder.getZip() + "").toUpperCase();

            if(OIDAsString.contains(key) ||
                    PIDAsString.contains(key) ||
                    state.contains(key) ||
                    plate.contains(key) ||
                    type.contains(key) ||
                    VIDAsString.contains(key) ||
                    visitorName.contains(key) ||
                    card.contains(key) ||
                    nameOnCard.contains(key) ||
                    expirationDate.contains(key) ||
                    zipCode.contains(key))
                results.add(order);
        }

        JsonElement output = GeneralResources.simplifyOrders(results);
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.OK).entity(outputAsString).build();
    }


}
