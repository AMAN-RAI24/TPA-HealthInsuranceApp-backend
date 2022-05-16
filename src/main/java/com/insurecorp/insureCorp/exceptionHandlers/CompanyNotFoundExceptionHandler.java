package com.insurecorp.insureCorp.exceptionHandlers;

import com.insurecorp.insureCorp.exceptionHandlers.exceptions.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class CompanyNotFoundExceptionHandler {
    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<String> handleCompanyNotFoundException(CompanyNotFoundException e){
        return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
