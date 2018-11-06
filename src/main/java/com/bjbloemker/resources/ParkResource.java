package com.bjbloemker.resources;
import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.*;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/parkpay/parks")
@Produces("application/json")
public class ParkResource {
    private static Gson gson = new Gson();
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    @GET
    public Response getParks() {
        return Response.status(200).entity(gson.toJson(MemoryManager.parks)).build();
    }

    @GET
    @Path("/{pid}")
    public Response getParkDetail(@PathParam("pid") String id) {
        ParkObj park = findParkById(id);
        if(park == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson((Park)park)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPark(String data){
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        LocationInfoObj location_info = localJsonParser.JsonToLocation(jsonObject.get("location_info").getAsJsonObject());
        ChargeInfoObj charge_info = localJsonParser.JsonToChargeInfo(jsonObject.get("payment_info").getAsJsonObject());

        ParkObj park = new Park(location_info, charge_info);
        System.out.println(park);
        MemoryManager.parks.add(park);
        return Response.status(Response.Status.OK).entity(gson.toJson(park.getPIDAsString())).build();
    }

    @DELETE
    @Path("/{pid}")
    public Response deletePark(@PathParam("pid") String id) {
        ParkObj park = findParkById(id);
        if(park != null){
            MemoryManager.parks.remove(park);
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //TODO: Refractor on park (reposonse type and method)
    private ParkObj findParkById(String id){
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            ParkObj park = MemoryManager.parks.get(i);
            if(park.getPIDAsString().equals(id)){
                return park;
            }
        }
        return null;
    }


}