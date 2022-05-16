package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.insurecorp.insureCorp.entities.UserPolicy;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserViewPolicyController {
    @Autowired
    UserPolicyRepository userViewPolicyRepository;

    @RequestMapping(path="/get-user-policy",method= RequestMethod.GET)
    public List<UserPolicy> getUserPolicy(){
        return userViewPolicyRepository.findAll();
    }
    @RequestMapping(path="/update-user-policy/{id}" , method= RequestMethod.PUT)
    public ResponseEntity<String> updateUserPolicy(@PathVariable(value = "id") String id , @RequestBody UserPolicy policy){
        UserPolicy obj=userViewPolicyRepository.save(policy);
        if(!obj.toString().isEmpty()){
            return new ResponseEntity<>("record updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("unable to update the record",HttpStatus.BAD_REQUEST);
    }

}
