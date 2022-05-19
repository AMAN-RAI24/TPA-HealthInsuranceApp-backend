package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.repositories.CompanyRepository;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserViewPolicyController {
    @Autowired
    UserPolicyRepository userViewPolicyRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    LoginService loginService;

    @RequestMapping(path="/get-user-policy",method= RequestMethod.GET)
    public UserPolicy getUserPolicy( @RequestHeader("Authorization") String jwt){
        User user = loginService.getUser(jwt);
        List<UserPolicy> userPolicyList=userViewPolicyRepository.findByUserOrderByUserPolicyIdDesc(user);

        GroupPolicy groupPolicy=new GroupPolicy();
        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
        if(!groupPolicyList.isEmpty() && !userPolicyList.isEmpty() ){
            return userPolicyList.get(0);
        } else if (!groupPolicyList.isEmpty()) {
            UserPolicy userPolicy=new UserPolicy();
            userPolicy.setUser(user);
            userPolicy.setGroupPolicy(groupPolicyList.get(0));
            userPolicy.setUserFamilyDetails(new ArrayList<>());
            userPolicy.setCoverage(0);
            userPolicy.setUserPolicyId(1);
            return userPolicy;
        }
        return null;
    }


    @RequestMapping(path="/set-user-policy",method=RequestMethod.POST)
    public String createUserPolicy(@RequestHeader("Authorization") String jwt, @RequestBody UserPolicyDTO policy){
        User user=loginService.getUser(jwt);
        System.out.println(user.getName());
        UserPolicy userPolicy=new UserPolicy();
        userPolicy.setUser(user);
        GroupPolicy groupPolicy=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany()).get(0);
        userPolicy.setGroupPolicy(groupPolicy);
        userPolicy.setCoverage(policy.getCoverage());
        userPolicy.setUserFamilyDetails(policy.getFamilyDetails());
        userViewPolicyRepository.save(userPolicy);
        return "ok";
    }

    @RequestMapping(path="/getpolicy/{id}",method=RequestMethod.GET)
    public UserPolicy getUserPolicy(@PathVariable("id") int id){
        return userViewPolicyRepository.getById(id);
    }
}
