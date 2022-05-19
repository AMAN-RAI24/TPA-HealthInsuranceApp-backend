package com.insurecorp.insureCorp.exceptionHandlers;

import com.insurecorp.insureCorp.exceptionHandlers.exceptions.UnidentifiedRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class RoleExceptionHandler {
    @ExceptionHandler(UnidentifiedRoleException.class)
    public ResponseEntity<String> handleRoleException(UnidentifiedRoleException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
