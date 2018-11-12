package com.bjbloemker.resources;
import com.bjbloemker.api.*;
import com.bjbloemker.core.*;
import com.google.gson.*;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/parkpay/parks")
@Produces("application/json")
public class ParkResource {
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();  //gson parser
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    @GET
    @Path("/{pid}")
    public Response getParkDetail(@PathParam("pid") String id) {
        ParkObj park = GeneralResources.findParkById(id);
        if(park == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson((Park)park)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPark(String data){
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        JsonObject locationInfoAsJsonObject = jsonObject.get("location_info").getAsJsonObject();
        LocationInfoObj location_info = localJsonParser.JsonToLocation(locationInfoAsJsonObject);

        JsonObject chargeInfoAsJsonObject = jsonObject.get("payment_info").getAsJsonObject();
        ChargeInfoObj charge_info = localJsonParser.JsonToChargeInfo(chargeInfoAsJsonObject);

        ParkObj park = new Park(location_info, charge_info);
        MemoryManager.parks.add(park);

        JsonObject output = new JsonObject();
        output.addProperty("pid", park.getPIDAsString());
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.OK).entity(outputAsString).build();
    }

    @DELETE
    @Path("/{pid}")
    public Response deletePark(@PathParam("pid") String id) {
        ParkObj park = GeneralResources.findParkById(id);
        if(park != null){
            MemoryManager.parks.remove(park);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response searchPark(@QueryParam("key") String key) {

        if(key == null || key.length() == 0){
            JsonElement output = GeneralResources.parksWithoutProperty("payment_info");
            String outputAsString = gson.toJson(output);
            return Response.status(Response.Status.OK).entity(outputAsString).build();
        }


        key = key.toUpperCase();
        ArrayList<ParkObj> results = new ArrayList<ParkObj>();

        for(int i =0; i< MemoryManager.parks.size(); i++){
            ParkObj park = (Park) MemoryManager.parks.get(i);
            LocationInfo locationInfo = (LocationInfo) park.getLocationInfo();
            String parkName = locationInfo.getName().toUpperCase();
            String parkAddr = locationInfo.getAddress().toUpperCase();
            String parkPhone = locationInfo.getPhone().toUpperCase();
            String parkWeb = locationInfo.getWeb().toUpperCase();

            GeoCordsObj geoCords = locationInfo.getGeo();
            String parkLat = (geoCords.getLat() + "").toUpperCase();
            String parkLng = (geoCords.getLng() + "").toUpperCase();

            if(parkName.contains(key) ||
                    parkAddr.contains(key) ||
                    parkPhone.contains(key) ||
                    parkWeb.contains(key) ||
                    parkLat.contains(key) ||
                    parkLng.contains(key))
                results.add(park);
        }

        JsonElement output = GeneralResources.parksWithoutProperty("payment_info");
        String outputAsString = gson.toJson(output);
        return Response.status(Response.Status.OK).entity(outputAsString).build();
    }

    @POST
    @Path("/{pid}/notes")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNoteWithPark(String data, @PathParam("pid") String pid){
        //TODO:BAD REQUEST
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        String vid = jsonObject.get("vid").getAsString();
        String title = jsonObject.get("title").getAsString();
        String content = jsonObject.get("text").getAsString();

        NoteObj note = new Note(title, content, pid, vid);

        MemoryManager.notes.add(note);

        JsonObject output = new JsonObject();
        output.addProperty("nid", note.getNIDAsString());

        return Response.status(Response.Status.CREATED).entity(gson.toJson(output)).build();
    }

    @GET
    @Path("/{pid}/notes")
    public Response notesByPark(@PathParam("pid") String pid){
        JsonArray output = new JsonArray();

        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
            String PIDForNote = note.getPIDAsString();
            if(PIDForNote.equals(pid)){
                JsonObject simplifiedNote = new JsonObject();
                String nid = note.getNIDAsString();
                String date = note.getDate();
                String title = note.getTitle();

                simplifiedNote.addProperty("nid", nid);
                simplifiedNote.addProperty("date", date);
                simplifiedNote.addProperty("title", title);
                output.add(simplifiedNote);
            }
        }


        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }

    @GET
    @Path("/{pid}/notes/{nid}")
    public Response noteWithPark(@PathParam("pid") String pid, @PathParam("nid") String nid){
        NoteObj note = GeneralResources.findNoteByNoteId(nid);
        ParkObj park = GeneralResources.findParkById(pid);

        if(note == null || park == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(!note.getPIDAsString().equals(pid))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson(note)).build();
    }

}