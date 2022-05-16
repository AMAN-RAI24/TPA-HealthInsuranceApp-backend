package com.insurecorp.insureCorp.exceptionHandlers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ExceptionBodyV2 {
    private int status;
    private String message;
    private Map<String,Object> data;
    private long timeStamp;

    public ExceptionBodyV2(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ExceptionBodyV2(int status, String message, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
