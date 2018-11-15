package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.ParkObj;
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
public class NotesResource {
    private static Gson gson = new Gson();
    private static com.google.gson.JsonParser parser = new com.google.gson.JsonParser();  //gson parser
    private static com.bjbloemker.resources.JsonParser localJsonParser;

    @GET
    public Response searchNote(@QueryParam("key") String key) {
        boolean keyPresent = true;

        if(key == null || key.length() == 0)
            keyPresent = false;

        ArrayList<NoteObj> results = new ArrayList<>();

        if(keyPresent) {
            key = key.toUpperCase();
            for (int i = 0; i < MemoryManager.notes.size(); i++) {
                NoteObj note = MemoryManager.notes.get(i);
                String title = note.getTitle();
                String content = note.getText();
                String date = note.getDate();

                if (title.contains(key) ||
                        content.contains(key) ||
                        date.contains(key))
                    results.add(note);
            }
        }

        JsonArray primaryArray = new JsonArray();
        for(int i = 0; i < MemoryManager.parks.size(); i++){
            JsonObject outputObject = new JsonObject();
            ParkObj currentPark = MemoryManager.parks.get(i);
            String currentPID = currentPark.getPIDAsString();

            outputObject.addProperty("pid", currentPID);

            //build notes array
            JsonArray noteArray = new JsonArray();
            ArrayList<NoteObj> notesByPark = GeneralResources.getAllNotesFromPark(currentPID);

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
        NoteObj note = GeneralResources.findNoteByNoteId(id);
        if (note == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson((Note) note)).build();
    }


    //TODO: UPDATE NOTE

}
