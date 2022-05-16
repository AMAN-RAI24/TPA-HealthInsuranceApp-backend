package com.insurecorp.insureCorp.exceptionHandlers;

import com.insurecorp.insureCorp.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<Object> handleCustomExceptions(CustomException ex){
        ExceptionBody errorResponse = new ExceptionBody();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }
}
