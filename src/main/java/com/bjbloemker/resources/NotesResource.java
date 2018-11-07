package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.core.MemoryManager;
import com.bjbloemker.core.Note;
import com.google.gson.Gson;

import javax.ws.rs.*;
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

        if(key == null || key.length() == 0)
            return Response.status(Response.Status.OK).entity(gson.toJson(MemoryManager.notes)).build();

        key = key.toUpperCase();
        ArrayList<NoteObj> results = new ArrayList<NoteObj>();

        for(int i =0; i< MemoryManager.notes.size(); i++){
            NoteObj note = MemoryManager.notes.get(i);
            String title = note.getTitle();
            String content = note.getContent();
            String date = note.getDate();

            if(title.contains(key) ||
                    content.contains(key) ||
                    date.contains(key))
                results.add(note);
        }

        return Response.status(Response.Status.OK).entity(gson.toJson(results)).build();
    }

    @GET
    @Path("/{nid}")
    public Response getNoteDetail(@PathParam("nid") String id) {
        NoteObj note = findNoteById(id);
        if (note == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(gson.toJson((Note) note)).build();
    }


    //TODO: FIX DUPLICATE CODE
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
