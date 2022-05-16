package com.insurecorp.insureCorp.exceptions;

import java.util.Map;

public class CustomExceptionV2 extends RuntimeException{
    public CustomExceptionV2() {
        super();
    }

    public CustomExceptionV2(String message, Integer status) {
        super(message);
        this.status = status;
    }
    public CustomExceptionV2(String message, Integer status, Map<String,Object> data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public CustomExceptionV2(String message, Throwable cause) {
        super(message, cause);
    }
    private Integer status;
    private Map<String,Object> data;

    public Integer getStatus() {
        return status;
    }
    public Map<String,Object> getData(){
        return data;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
