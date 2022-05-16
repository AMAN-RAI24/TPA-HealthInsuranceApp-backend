package com.insurecorp.insureCorp.exceptions;

public class CustomException extends RuntimeException {
    public CustomException() {
        super();
    }

    public CustomException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
