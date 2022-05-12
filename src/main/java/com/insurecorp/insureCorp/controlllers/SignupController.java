package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.exceptions.UserAlreadyExistException;
import com.insurecorp.insureCorp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SignupController {
    @Autowired
    UserRepository userrepository;
    User user;
    @RequestMapping(path="/signup",method= RequestMethod.POST)
    public ResponseEntity<String> collectUserDetailsAndRegister(@RequestBody User user){
        List<User> checkifuserexist=userrepository.findByEmail(user.getEmail());
        List<User> checkifuserexistwithmobile=userrepository.findByMobileNumber(user.getMobileNumber());
        if(!checkifuserexist.isEmpty() || !checkifuserexistwithmobile.isEmpty()){
            throw new UserAlreadyExistException("user already exist with email provided");
        }
            User saveduser = userrepository.save(user);
            ResponseEntity<String> response=new ResponseEntity<>("user registration successful",HttpStatus.OK);
            return response;
    }
    @RequestMapping(path="/getuser",method=RequestMethod.GET)
    public List<User> getEveryUser(){
        return userrepository.findAll();
    }
}
