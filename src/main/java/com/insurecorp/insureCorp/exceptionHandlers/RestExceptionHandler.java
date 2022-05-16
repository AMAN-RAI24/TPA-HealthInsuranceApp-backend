package com.insurecorp.insureCorp.exceptionHandlers;

import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.exceptions.CustomExceptionV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;

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

    @ExceptionHandler()
    public ResponseEntity<Object> handleCustomExceptionsV2(CustomExceptionV2 ex){
        ExceptionBodyV2 errorResponse = new ExceptionBodyV2();
        errorResponse.setStatus(ex.getStatus());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setData(ex.getData());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }


}
