package com.bjbloemker.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class NoteObj {
    protected UUID nid;
    protected String pid;
    protected String vid;
    protected String date;
    protected String title;
    protected String text;



    public NoteObj(String title, String text, String pid, String vid) {
        this.nid = UUID.randomUUID();
        this.date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        this.title = title;
        this.text = text;
        this.pid = pid;
        this.vid = vid;
    }

    public String getNIDAsString() {
        return nid.toString();
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPIDAsString() {
        return pid;
    }
}
