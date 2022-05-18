package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.entities.UserPolicy;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ManagerOrUserController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserPolicyRepository userPolicyRepository;

    @GetMapping("/profile")
    Map<String,Object> getProfile(@RequestHeader("Authorization") String jwt) {
        User user = loginService.getUser(jwt);
        if (Objects.isNull(user)){
            throw new CustomException("Invalid Token",403);
        }
        Map<String,Object> payload = new HashMap<>();
        payload.put("userDetails",Map.of("employeeId",user.getEmployeeId(),"name", user.getName(),"dob",user.getDate(),"company",user.getCompany().getCompanyName(),"mobileNumber",user.getMobileNumber()));
        List<GroupPolicy> list = user.getCompany().getGroupPolicies();
        if(list.isEmpty()){
            throw new CustomException("Company hasn't bought any policies yet.",404);
        }
        GroupPolicy latest = list.get(0);


        for(GroupPolicy groupPolicy: list){
            if (groupPolicy.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicy;
            }
        }

        UserPolicy userPolicy =  userPolicyRepository.findByGroupPolicyAndUser(latest,user);

//        UserPolicy userPolicy =  userPolicyRepository.findByUser(user);

        if (Objects.isNull(userPolicy)){
            payload.put("planDetails",null);
            return payload;
        }
        GroupPolicy groupPolicy =  userPolicy.getGroupPolicy();
        payload.put("planDetails", Map.of("policyName",groupPolicy.getPolicyName(),"coverage",groupPolicy.getCoverage(),"premium",userPolicy.getCoverage()));
        payload.put("familyDetails",userPolicy.getUserFamilyDetails());
        return payload;
    }


}