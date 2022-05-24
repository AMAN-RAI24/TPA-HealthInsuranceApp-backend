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
import java.util.Objects;

@RestController
public class UserViewPolicyController {
    @Autowired
    UserPolicyRepository userViewPolicyRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserPolicyRepository userPolicyRepository;
    @Autowired
    LoginService loginService;

    @RequestMapping(path="/get-user-policy",method= RequestMethod.GET)
    public UserPolicy getUserPolicy( @RequestHeader("Authorization") String jwt){
        User user = loginService.getUser(jwt);
        List<UserPolicy> userPolicyList=userViewPolicyRepository.findByUserOrderByUserPolicyIdDesc(user);



        GroupPolicy groupPolicy=new GroupPolicy();
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompany(user.getCompany());
        List<GroupPolicy> groupPolicyList = groupPolicyRepository.findGroupPolicyByCompanyAndStatus(user.getCompany(),"APPROVED");

        GroupPolicy latest = groupPolicyList.get(0);


        for(GroupPolicy groupPolicyTemp: groupPolicyList){
            if (groupPolicyTemp.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicyTemp;
            }
        }
        if(!groupPolicyList.isEmpty() && !userPolicyList.isEmpty() ){
            if(userPolicyList.get(0).getGroupPolicy().getGroupPolicyId() == latest.getGroupPolicyId()){

                System.out.println("INSIDe if above wala");
                return userPolicyList.get(0);
            }
            else{
                UserPolicy userPolicy=new UserPolicy();
                userPolicy.setUser(user);

                //ADDIng started
                System.out.println("INSIDe elseif above wala");

                userPolicy.setGroupPolicy(latest);
                //END
                userPolicy.setUserFamilyDetails(new ArrayList<>());
                userPolicy.setCoverage(0);
                userPolicy.setUserPolicyId(1);
                return userPolicy;
            }
        } else if (!groupPolicyList.isEmpty()) {
            UserPolicy userPolicy=new UserPolicy();
            userPolicy.setUser(user);

            //ADDIng started
            System.out.println("INSIDe elseif below wala");

            userPolicy.setGroupPolicy(latest);
            //END
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
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
        List<GroupPolicy> groupPolicyList = groupPolicyRepository.findGroupPolicyByCompanyAndStatus(user.getCompany(),"APPROVED");
        GroupPolicy latest = groupPolicyList.get(0);


        for(GroupPolicy groupPolicyTemp: groupPolicyList){
            if (groupPolicyTemp.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicyTemp;
            }
        }

        GroupPolicy groupPolicy =  latest;


//        Finding existing user policy

        UserPolicy fetchedUserPolicy = userPolicyRepository.findByGroupPolicyAndUser(groupPolicy,user);
        if (!Objects.isNull(fetchedUserPolicy)){
          userPolicy.setUserPolicyId(fetchedUserPolicy.getUserPolicyId());
        }

        //Editing ends here
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
