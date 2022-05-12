package com.insurecorp.insureCorp.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(String companyname){
        super("company not found with " + companyname);
    }
}
