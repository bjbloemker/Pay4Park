package com.bjbloemker.core;

public class Note {
    String NID;
    String date;
    String title;
    String content;

    public Note(String NID, String date, String title, String content) {
        this.NID = NID;
        this.date = date;
        this.title = title;
        this.content = content;
    }
}
