package com.insurecorp.insureCorp.exceptionHandlers.exceptions;

public class UnidentifiedRoleException extends RuntimeException{
    public UnidentifiedRoleException(String role){
        super("invalid role " + role);
    }
}
