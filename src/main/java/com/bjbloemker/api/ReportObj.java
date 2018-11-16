package com.bjbloemker.api;

public class ReportObj {
    private String rid;
    private String title;

    public ReportObj(String rid, String title) {
        this.rid = rid;
        this.title = title;
    }

    public String getRid() {
        return rid;
    }

    public String getTitle() {
        return title;
    }
}
