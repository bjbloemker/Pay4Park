package com.bjbloemker.resources;

import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parkpay/orders")
@Produces("application/json")
public class OrderResource {
    private static Gson gson = new Gson();
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();  //gson parser
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    @GET
    @Path("/{oid}")
    public Response getOrderDetail(@PathParam("oid") String id) {
        OrderObj order = GeneralResources.findOrderById(id);
        if(order == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        JsonObject output = new JsonObject();

        String oid = order.getOIDAsString();
        String pid = order.getPIDAsString();

        VehicleObj vehicle = order.getVehicle();
        VisitorObj visitor = order.getVisitor();
        PaymentInfoObj paymentInfo = visitor.getPaymentInfo();
        PaymentProcessingObj paymentProcessing = order.getPaymentProcessing();
        ParkObj park = GeneralResources.findParkById(pid);

        double amount = GeneralResources.calculateCost(vehicle, park);
        String vid = visitor.getVIDAsString();
        String date = order.getDate();

        JsonObject orderVehicle = new JsonObject();

        orderVehicle.addProperty("state", vehicle.getState());
        orderVehicle.addProperty("plate", vehicle.getPlate());
        orderVehicle.addProperty("type", vehicle.getType());

        JsonObject orderVisitor = new JsonObject();

        orderVisitor.addProperty("name", visitor.getName());
        orderVisitor.addProperty("email", visitor.getEmail());
        JsonObject orderVisitorPaymentInfo = new JsonObject();
        orderVisitorPaymentInfo.addProperty("card", ((PaymentInfo) paymentInfo).getHiddenCard());
        orderVisitorPaymentInfo.addProperty("name_on_card", paymentInfo.getNameOnCard());
        orderVisitorPaymentInfo.addProperty("expiration_date", paymentInfo.getExpirationDate());
        orderVisitorPaymentInfo.addProperty("zip", paymentInfo.getZip());
        orderVisitor.add("payment_info",orderVisitorPaymentInfo);

        JsonObject orderPaymentProcessing = new JsonObject();

        orderPaymentProcessing.addProperty("card_transaction_id", paymentProcessing.getCardTransactionID());
        orderPaymentProcessing.addProperty("date_and_time", paymentProcessing.getDateAndTime());

        //put it all together

        output.addProperty("oid", oid);
        output.addProperty("pid", pid);
        output.addProperty("amount", amount);
        output.addProperty("vid", vid);
        output.addProperty("date", date);
        output.add("vehicle", orderVehicle);
        output.add("visitor", orderVisitor);
        output.add("payment_processing", orderPaymentProcessing);

        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(String data){
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        String pid = jsonObject.get("pid").getAsString();

        JsonObject vehicleAsJsonObject = jsonObject.get("vehicle").getAsJsonObject();
        VehicleObj vehicle = localJsonParser.JsonToVehicle(vehicleAsJsonObject);

        JsonObject visitorAsJsonObject = jsonObject.get("visitor").getAsJsonObject();
        VisitorObj visitor = localJsonParser.JsonToVisitor(visitorAsJsonObject);

        OrderObj order;
        if(MemoryManager.requestAddToVisitor(visitor) == null){
            //find the existing visitor
            VisitorObj existingVisitor = GeneralResources.findVisitorByEmail(visitor.getEmail());
            order = new Order(pid, vehicle, existingVisitor);
        }else{
            order = new Order(pid, vehicle, visitor);
        }

        MemoryManager.orders.add(order);


        JsonObject output = new JsonObject();
        output.addProperty("oid", order.getPIDAsString());
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.CREATED).entity(outputAsString).header("Location", "parkpay/orders/" + order.getOIDAsString()).build();
    }

    @GET
    public Response searchOrder(@QueryParam("key") String key) {
        if(key == null || key.length() == 0){
            JsonElement output = GeneralResources.simplifyOrders(MemoryManager.orders);
            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();
        }

        //TODO:Fix all of it
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
            String visitorEmail = visitor.getEmail().toUpperCase();

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
                    visitorEmail.contains(key) ||
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
