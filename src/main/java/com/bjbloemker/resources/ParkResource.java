package com.bjbloemker.resources;
import com.bjbloemker.api.ChargeInfoObj;
import com.bjbloemker.api.LocationInfoObj;
import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.*;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

    //@GET
    //public Response getParks() {
        //return Response.status(200).entity(gson.toJson(MemoryManager.parks)).build();
    //}

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
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        LocationInfoObj location_info = localJsonParser.JsonToLocation(jsonObject.get("location_info").getAsJsonObject());
        ChargeInfoObj charge_info = localJsonParser.JsonToChargeInfo(jsonObject.get("payment_info").getAsJsonObject());

        ParkObj park = new Park(location_info, charge_info);
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

    @GET
    public Response searchPark(@QueryParam("key") String key) {

        if(key == null || key.length() == 0)
            return Response.status(Response.Status.OK).entity(gson.toJson(MemoryManager.parks)).build();

        key = key.toUpperCase();
        ArrayList<ParkObj> results = new ArrayList<ParkObj>();

        for(int i =0; i< MemoryManager.parks.size(); i++){
            ParkObj park = (Park) MemoryManager.parks.get(i);
            LocationInfo locationInfo = (LocationInfo) park.getLocationInfo();
            String parkName = locationInfo.getName().toUpperCase();
            String parkAddr = locationInfo.getAddress().toUpperCase();
            String parkPhone = locationInfo.getPhone().toUpperCase();
            String parkWeb = locationInfo.getWebsite().toUpperCase();
            String parkLat = (locationInfo.getLat() + "").toUpperCase();
            String parkLng = (locationInfo.getLng() + "").toUpperCase();

            if(parkName.contains(key) ||
                    parkAddr.contains(key) ||
                    parkPhone.contains(key) ||
                    parkWeb.contains(key) ||
                    parkLat.contains(key) ||
                    parkLng.contains(key))
                results.add(park);
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(results)).build();
    }

    @POST
    @Path("/{pid}/notes")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNoteWithPark(String data, @PathParam("pid") String pid){
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();

        String vid = jsonObject.get("vid").getAsString();
        String title = jsonObject.get("title").getAsString();
        String content = jsonObject.get("text").getAsString();

        NoteObj note = new Note(title, content, pid, vid);

        MemoryManager.notes.add(note);
        String output = new String(note.getNIDAsString());
        return Response.status(Response.Status.OK).entity(gson.toJson(output)).build();
    }

    @GET
    @Path("/{pid}/notes")
    public Response notesByPark(@PathParam("pid") String pid){
        ArrayList<NoteObj> results = new ArrayList<NoteObj>();

        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
            String PIDForNote = note.getPIDAsString();
            if(PIDForNote.equals(pid)){
                results.add(note);
            }
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(results)).build();
    }

    @GET
    @Path("/{pid}/notes/{nid}")
    public Response noteWithPark(@PathParam("pid") String pid, @PathParam("nid") String nid){
        /*
        200 (OK).
        400 (Bad request) if [nid] and [pid] are valid but the note [nid] is not associated with park [pid].
        404 (Not found) if the park identified by [pid] doesn't exist, or the note [nid] doesn't exist.
         */
        NoteObj note = findNoteById(nid);
        ParkObj park = findParkById(pid);

        if(note == null || park == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if(!note.getPIDAsString().equals(pid))
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson(park)).build();
    }


    private ParkObj findParkById(String id){
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            ParkObj park = MemoryManager.parks.get(i);
            if(park.getPIDAsString().equals(id)){
                return park;
            }
        }
        return null;
    }


    private NoteObj findNoteById(String id){
        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
            if(note.getPIDAsString().equals(id)){
                return note;
            }
        }
        return null;
    }


}