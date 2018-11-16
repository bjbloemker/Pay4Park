package com.bjbloemker.resources;

import com.bjbloemker.api.ErrorObj;
import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.ParkObj;
import com.bjbloemker.core.Error;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.Note;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/parkpay/notes")
@Produces("application/json")
public class NotesResource extends NotesServices{
    private static Gson gson = new Gson();
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();  //gson parser
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    @GET
    public Response searchNote(@QueryParam("key") String key) {
        boolean keyPresent = true;

        if(key == null || key.length() == 0)
            keyPresent = false;


        ArrayList<NoteObj> results = new ArrayList<>();
        if(keyPresent){
            key = key.toUpperCase();
            results = GeneralServices.searchNotes(key, null, null);
        }


        JsonArray primaryArray = new JsonArray();
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            JsonObject outputObject = new JsonObject();
            ParkObj currentPark = MemoryManager.parks.get(i);
            String currentPID = currentPark.getPIDAsString();

            outputObject.addProperty("pid", currentPID);

            //build notes array
            JsonArray noteArray = new JsonArray();
            ArrayList<NoteObj> notesByPark = getAllNotesFromPark(currentPID);

            for(int j = 0; j < notesByPark.size(); j++){
                NoteObj currentNote = notesByPark.get(j);
                if(results.contains(currentNote) || keyPresent == false){
                    JsonElement noteToAdd = gson.toJsonTree(currentNote);
                    noteToAdd.getAsJsonObject().remove("pid");
                    noteToAdd.getAsJsonObject().remove("vid");
                    noteToAdd.getAsJsonObject().remove("text");

                    noteArray.add(noteToAdd);
                }
            }

            if(noteArray.size() != 0){
                outputObject.add("notes", noteArray);
                primaryArray.add(outputObject);
            }
        }


        return Response.status(Response.Status.OK).entity(gson.toJson(primaryArray)).build();
    }

    @GET
    @Path("/{nid}")
    public Response getNoteDetail(@PathParam("nid") String id) {
        NoteObj note = GeneralServices.findNoteByNoteId(id);
        if (note == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(gson.toJson((Note) note)).build();
    }


    @PUT
    @Path("/{nid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNoteWithPark(String data, @PathParam("nid") String nid){
        JsonObject jsonObject = parser.parse(data).getAsJsonObject();
        ErrorObj error = new Error("http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation", "Your request data didn't pass validation", 400, "/notes/"+nid);

        NoteObj oldNote = GeneralServices.findNoteByNoteId(nid);

        if(oldNote == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        String pid = oldNote.getPIDAsString();
        String vid;
        String title;
        String content;

        try {
            vid = jsonObject.get("vid").getAsString();
            title = jsonObject.get("title").getAsString();
            content = jsonObject.get("text").getAsString();
        }catch (NullPointerException e){
            ((Error) error).setDetail("A note must have a vid, title, and text to be updated");
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
        }

        NoteObj note = new Note(title, content, pid, vid);
        note.setNid(oldNote.getNIDAsString());
        MemoryManager.notes.remove(oldNote);
        MemoryManager.notes.add(note);
        return Response.status(Response.Status.OK).build();
    }

}
