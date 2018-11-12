package com.bjbloemker.core;

import com.bjbloemker.api.NoteObj;

public class Note extends NoteObj{

    public Note(String title, String content, String PID, String VID) {
        super(title, content, PID, VID);
    }

    @Override
    public String toString() {
        return "Note{" +
                "nid=" + nid +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pid='" + pid + '\'' +
                ", vid='" + vid + '\'' +
                '}';
    }
}
