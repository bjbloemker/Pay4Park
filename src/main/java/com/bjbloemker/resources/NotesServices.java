package com.bjbloemker.resources;

import com.bjbloemker.api.NoteObj;
import com.bjbloemker.core.MemoryManager;

import java.util.ArrayList;

public abstract class NotesServices {

    //note res
    protected static ArrayList<NoteObj> getAllNotesFromPark(String pid){
        ArrayList<NoteObj> notes = new ArrayList<>();

        for(int i = 0; i < MemoryManager.notes.size(); i++){
            NoteObj currentNote = MemoryManager.notes.get(i);
            if(currentNote.getPIDAsString().equals(pid)){
                notes.add(currentNote);
            }
        }

        return notes;
    }

}
