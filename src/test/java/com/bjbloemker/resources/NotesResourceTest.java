package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.api.OrderObj;
import com.bjbloemker.api.VisitorObj;
import com.bjbloemker.core.MemoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class NotesResourceTest extends DataForTesting{

    @BeforeEach
    void init(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

    @Test
    void searchNoteNoResults() {
        buildParkAnsSetupNote();

        Response result = NotesResource.searchNote("thiswillneverbefound");
        String expected = "[]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void searchNoteNullKey(){
        buildParkAnsSetupNote();

        NoteObj note = MemoryManager.notes.get(0);

        Response result = NotesResource.searchNote(null);
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[{\"nid\":\""+note.getNIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+note.getTitle()+"\"}]}]";
        assertEquals(expected, result.getEntity());
    }

    @Test
    void searchNoteOK() {
        buildParkAnsSetupNote();

        NoteObj note = MemoryManager.notes.get(0);

        Response result = NotesResource.searchNote("text");
        String expected = "[{\"pid\":\""+pid+"\",\"notes\":[{\"nid\":\""+note.getNIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+note.getTitle()+"\"}]}]";
        assertEquals(expected, result.getEntity());
    }


    @Test
    void getNoteDetail() {
        buildParkAnsSetupNote();

        NoteObj note = MemoryManager.notes.get(0);

        Response result = NotesResource.getNoteDetail(note.getNIDAsString());
        String expected = "{\"nid\":\""+note.getNIDAsString()+"\",\"pid\":\""+note.getPIDAsString()+"\",\"vid\":\""+note.getVIDAsString()+"\",\"date\":\""+note.getDate()+"\",\"title\":\""+title+"\",\"text\":\""+text+"\"}";
        assertEquals(expected, result.getEntity());

    }

    @Test
    void getNoteDetailNotFound() {
        buildParkAnsSetupNote();
        Response result = NotesResource.getNoteDetail("not_a_read_identifier");
        assertEquals(404, result.getStatus());

    }

    private void buildParkAnsSetupNote(){
        pid = buildPark();
        String toSendToOrder = "{\"pid\": \""+pid+"\", \"vehicle\": {\"state\": \""+state+"\", \"plate\": \""+plate+"\", \"type\": \""+type+"\"}, \"visitor\": {\"name\": \""+name+"\", \"email\": \""+email+"\", \"payment_info\": {\"card\": \""+cardNumber+"\", \"name_on_card\": \""+name+"\", \"expiration_date\": \""+expiration+"\", \"zip\": "+zipCode+"}}}";

        OrderResource.createOrder(toSendToOrder);
        OrderObj order = MemoryManager.orders.get(0);

        String toSend = "{\"vid\": \""+order.getVisitor().getVIDAsString()+"\", \"title\": \""+title+"\", \"text\": \""+text+"\"}";
        ParkResource.createNoteWithPark(toSend, pid);
    }

    @AfterAll
    static void cleanUp(){
        MemoryManager.parks.clear();
        MemoryManager.orders.clear();
        MemoryManager.notes.clear();
        MemoryManager.visitors.clear();
    }

}