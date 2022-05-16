package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.User;

import com.insurecorp.insureCorp.exceptionHandlers.exceptions.UserAlreadyExistException;

import com.insurecorp.insureCorp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
public class SignupController {
    @Autowired
    UserRepository userrepository;
//    @Autowired
//    CompanyRepository companyRepository;
//    @Autowired
//    RoleRepository roleRepository;

    User user;
    @RequestMapping(path="/signup",method= RequestMethod.POST)
    public ResponseEntity<String> collectUserDetailsAndRegister(@RequestBody User user) throws ParseException {
//    public ResponseEntity<String> collectUserDetailsAndRegister(@RequestBody Map<String,String> mapObject) throws ParseException {
//
//        User user = new User();
//        user.setEmployeeId( mapObject.get("employeeId"));
//        user.setEmail( mapObject.get("email"));
//        user.setName( mapObject.get("name"));
//        user.setMobileNumber( mapObject.get("mobileNumber"));
//        user.setDate(new SimpleDateFormat("dd/MM/yyyy").parse( mapObject.get("date")));
//        user.setCompany(companyRepository.findByCompanyName(mapObject.get("company")).get(0));
//        user.setCompany(companyRepository.findByCompanyName(mapObject.get("company")));
//        user.setRole(roleRepository.findByRole(mapObject.get("role")).get(0));
//        user.setPassword(mapObject.get("password"));



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
