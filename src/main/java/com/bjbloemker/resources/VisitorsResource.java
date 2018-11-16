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
public class VisitorsResource {
    private static Gson gson = new Gson();

    @GET
    @Path("/{vid}")
    public Response getVisitorDetail(@PathParam("vid") String id) {
        VisitorObj visitor = GeneralServices.findVisitorById(id);
        if(visitor == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        JsonObject output = new JsonObject();

        output.addProperty("vid", visitor.getVIDAsString());

        JsonObject innerVisitor = new JsonObject();
        innerVisitor.addProperty("name", visitor.getName());
        innerVisitor.addProperty("email", visitor.getEmail());

        output.add("visitor", innerVisitor);

        ArrayList<OrderObj> ordersByVisitor = GeneralServices.getAllOrdersFromVisitor(visitor.getVIDAsString());
        JsonElement toPutOrders  = GeneralServices.superSimplifyOrders(ordersByVisitor);
        output.add("orders", toPutOrders);


        ArrayList<NoteObj> notesByVisitor = GeneralServices.getAllNotesFromVisitor(visitor.getVIDAsString());
        JsonElement toPutNotes  = GeneralServices.superSimplifyNotes(notesByVisitor);
        output.add("notes", toPutNotes);

        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }

    @GET
    public Response searchVisitors(@QueryParam("key") String key) {

        if(key == null || key.length() == 0){
            JsonElement output = GeneralServices.visitorsWithoutProperty(MemoryManager.visitors, "payment_info");
            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();
        }

        key = key.toUpperCase();
        ArrayList<VisitorObj> results = new ArrayList<>();

        for(int i =0; i < MemoryManager.visitors.size(); i++){
            VisitorObj visitor = MemoryManager.visitors.get(i);


            String VIDAsString = visitor.getVIDAsString().toUpperCase();
            String visitorName = visitor.getName().toUpperCase();
            String visitorEmail = visitor.getEmail().toUpperCase();


            PaymentInfoObj paymentInfoOfVisitorOfOrder = visitor.getPaymentInfo();
            String card = paymentInfoOfVisitorOfOrder.getCard().toUpperCase();
            String nameOnCard = paymentInfoOfVisitorOfOrder.getNameOnCard().toUpperCase();
            String expirationDate = paymentInfoOfVisitorOfOrder.getExpirationDate().toUpperCase();
            String zipCode = (paymentInfoOfVisitorOfOrder.getZip() + "").toUpperCase();

            if(VIDAsString.contains(key) ||
                    visitorName.contains(key) ||
                    visitorEmail.contains(key) ||
                    card.contains(key) ||
                    nameOnCard.contains(key) ||
                    expirationDate.contains(key) ||
                    zipCode.contains(key))
                results.add(visitor);
        }

        JsonElement output = GeneralServices.simplifyVisitors(results);
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.OK).entity(outputAsString).build();
    }
}
