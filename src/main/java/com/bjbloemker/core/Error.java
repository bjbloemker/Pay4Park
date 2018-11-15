package com.bjbloemker.core;

import com.bjbloemker.api.ErrorObj;

public class Error extends ErrorObj {

    public Error(String type, String title, int status, String instance) {
        super(type, title, null, status, instance);
    }

    public void setDetail(String detail){
        super.detail = detail;
    }

}
