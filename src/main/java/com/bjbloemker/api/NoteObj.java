package com.bjbloemker.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class NoteObj {
    protected UUID NID;
    protected String date;
    protected String title;
    protected String content;
    protected String PID;
    protected String VID;


    public NoteObj(String title, String content, String PID, String VID) {
        this.NID = UUID.randomUUID();
        this.date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        this.title = title;
        this.content = content;
        this.PID = PID;
        this.VID = VID;
    }

    public String getNIDAsString() {
        return NID.toString();
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPIDAsString() {
        return PID;
    }
}
