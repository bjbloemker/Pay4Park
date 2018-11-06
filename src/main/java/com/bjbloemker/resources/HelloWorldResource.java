package com.bjbloemker.resources;
import com.bjbloemker.core.*;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/parkpay/parks")
public class HelloWorldResource {
    private static Gson gson = new Gson();
    @GET
    @Path("{pid}")
    public String helloWorld(@PathParam("pid") String name) {
        return "Hello " + name + "!";
    }
    //double [] prices = {1,2};
    //        Park pi = new Park(new LocationInfo("name", "reg", "add", "5135025598","web", 1, 2),
    //                new ChargeInfo(prices,prices,prices));

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(String data){
        //System.out.println("=======BEGIN DATA=======\n" + data + "\n========END DATA=======");
        //Park p = gson.fromJson(data, Park.class);
        System.out.println("====START DEBUG=====");
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        LocationInfo location_info = JsonToLocationObj(jsonObject.get("location_info").getAsJsonObject());
        ChargeInfo payment_info = JsonToChargeInfoObj(jsonObject.get("payment_info").getAsJsonObject());

        Park park = new Park(location_info, payment_info);
        System.out.println(park);
        MemoryManager.parks.add(park);
        System.out.println("=====END DEBUG======");
        return Response.status(200).entity(gson.toJson(park.getPID())).build();
    }

    private LocationInfo JsonToLocationObj(JsonObject locationJson){
        LocationInfo location = new LocationInfo();
        location.setAddress(locationJson.get("address").getAsString());
        location.setName(locationJson.get("name").getAsString());
        location.setPhone(locationJson.get("phone").getAsString());
        location.setWebsite(locationJson.get("web").getAsString());
        return location;
    }

    private ChargeInfo JsonToChargeInfoObj(JsonObject locationJson){
        ChargeInfo chargeInfo = new ChargeInfo();

        double [] motorcyclePrices = new double[2];
        JsonArray motorcyclePricesAsJsonArray = locationJson.get("motorcycle").getAsJsonArray();
        for(int i = 0; i < motorcyclePrices.length; i++)
            motorcyclePrices[i] = motorcyclePricesAsJsonArray.get(i).getAsDouble();

        double [] carPrices = new double[2];
        JsonArray carPricesAsJsonArray = locationJson.get("car").getAsJsonArray();
        for(int i = 0; i < carPrices.length; i++)
            carPrices[i] = carPricesAsJsonArray.get(i).getAsDouble();

        double [] rvPrices = new double[2];
        JsonArray rvPricesAsJsonArray = locationJson.get("rv").getAsJsonArray();
        for(int i = 0; i < rvPrices.length; i++)
            rvPrices[i] = rvPricesAsJsonArray.get(i).getAsDouble();

        chargeInfo.setMotorcyclePrice(motorcyclePrices);
        chargeInfo.setCarPrice(carPrices);
        chargeInfo.setRvPrice(rvPrices);

        return chargeInfo;
    }


}