package com.insurecorp.insureCorp.exceptions;

public class UnidentifiedRoleException extends RuntimeException{
    public UnidentifiedRoleException(String role){
        super("invalid role " + role);
    }
}
