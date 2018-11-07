package com.bjbloemker.core;

import com.bjbloemker.api.NoteObj;

public class Note extends NoteObj{

    public Note(String title, String content, String PID, String VID) {
        super(title, content, PID, VID);
    }

    @Override
    public String toString() {
        return "Note{" +
                "NID=" + NID +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", PID='" + PID + '\'' +
                ", VID='" + VID + '\'' +
                '}';
    }
}
