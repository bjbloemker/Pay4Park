package com.bjbloemker.api;

public abstract class ErrorObj {
    protected String type;
    protected String title;
    protected String detail;
    protected int status;
    protected String instance;

    public ErrorObj(String type, String title, String detail, int status, String instance) {
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.instance = instance;
    }

}
