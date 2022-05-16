package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/hw")
    public String helloWorld()
    {
        return "Hello World";
    }


    @GetMapping("/get/{id}")
    User getUser(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }
}
