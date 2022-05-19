package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.exceptions.CustomExceptionV2;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.responseModels.Policies;
import com.insurecorp.insureCorp.responseModels.PolicyAddedResponse;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PolicyController {
    @Autowired
    private GroupPolicyRepository groupPolicyRepository;
    @Autowired
    private LoginService loginService;

    @Autowired
    UserPolicyRepository userPolicyRepository;
    @PostMapping("/add/policy")
    public PolicyAddedResponse  addPolicy(@RequestBody GroupPolicy groupPolicy,@RequestHeader("Authorization") String jwt)
    {
        System.out.println(jwt);
        User user = loginService.getUser(jwt);
        groupPolicy.setManager(user);
        groupPolicy.setCompany(user.getCompany());
        GroupPolicy savedGroupPolicy = groupPolicyRepository.save(groupPolicy);
        PolicyAddedResponse policyAddedResponse = new PolicyAddedResponse();
        policyAddedResponse.setPolicyId(savedGroupPolicy.getGroupPolicyId());
        policyAddedResponse.setPolicyName(savedGroupPolicy.getPolicyName());
        return policyAddedResponse;
    }
    @GetMapping("/get-policy")
    public GroupPolicy getPolicy(@RequestParam int id)
    {
        System.out.println(id);
        GroupPolicy groupPolicy = groupPolicyRepository.getById(id);
        System.out.println(groupPolicy.getPolicyName());
        return groupPolicy;
    }
    @GetMapping("/get-policy-by-company")
    public List<Policies> getPolicyByCompany(@RequestHeader("Authorization") String jwt)
    {
        User manager = loginService.getUser(jwt);
        List<GroupPolicy> groupPolicies = groupPolicyRepository.findGroupPolicyByCompany(manager.getCompany());
        List<Policies> policies = new ArrayList<>();
        for (GroupPolicy item: groupPolicies)
        {
            Policies policy = new Policies();
            int benefits =0;
            if(item.getMaximumClaim().getAngiography()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getBypassSurgery()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getCataractSurgery()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getCovidCoverage()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getHospitalization()!=0.0)
            {
                benefits++;
            }
            policy.setPolicyName(item.getPolicyName());
            policy.setCoverage(item.getCoverage());
            policy.setBenefits(benefits);
            policy.setPolicyId(item.getGroupPolicyId());
            policies.add(policy);
        }
        return policies;
    }
    @GetMapping("/get-base-plans")
    public List<Policies> getBasePlans()
    {
        List<GroupPolicy> groupPolicies = groupPolicyRepository.findGroupPolicyByType("BASE");
        List<Policies> policies = new ArrayList<>();
        for (GroupPolicy item: groupPolicies)
        {
            Policies policy = new Policies();
            int benefits =0;
            if(item.getMaximumClaim().getAngiography()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getBypassSurgery()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getCataractSurgery()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getCovidCoverage()!=0.0)
            {
                benefits++;
            }
            if(item.getMaximumClaim().getHospitalization()!=0.0)
            {
                benefits++;
            }
            policy.setPolicyName(item.getPolicyName());
            policy.setCoverage(item.getCoverage());
            policy.setBenefits(benefits);
            policy.setPolicyId(item.getGroupPolicyId());
            policies.add(policy);
        }
        return policies;
    }

    @PostMapping({"/add/userPolicy","/add/top-up","/add/coverage"})
    UserPolicy addUserPolicy(@RequestHeader("Authorization") String jwt, @RequestBody Map<String,Object> requestBody){
//    List<UserFamilyDetails> addUserPolicy(@RequestHeader("Authorization") String jwt, @RequestBody Map<String,Object> requestBody){
        User user = loginService.getUser(jwt);
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

        UserPolicy userPolicyFromDb = userPolicyRepository.findByGroupPolicyAndUser(latest,user);
//        V1
//        List<UserFamilyDetails> dumbFamilyDetailsList =  (List)requestBody.get("familyMembers");

//        Test


//        List<UserFamilyDetails> dumbFamilyDetailsList = new ArrayList<>();
//
//        UserFamilyDetails ob1 = new UserFamilyDetails();
//        ob1.setName("my name");
//        ob1.setAge(21);
//        ob1.setRelation("myself");
//        ob1.setImageUrl("url");
//        ob1.setPhoneNumber("323298239");
//
//        UserFamilyDetails ob2 = new UserFamilyDetails();
//        ob2.setName("not my hhhhhhhhhhhhhhh");
//        ob2.setAge(21);
//        ob2.setRelation("not myself");
//        ob2.setImageUrl("not url");
//        ob2.setPhoneNumber("32323555555");
//
//        dumbFamilyDetailsList.add(ob1);
//        dumbFamilyDetailsList.add(ob2);


//        V2
        List<Object> dumbFamilyDetailsList = (List) requestBody.get("familyMembers");
        List<UserFamilyDetails> familyDetails = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object obj : dumbFamilyDetailsList) {
            UserFamilyDetails temp = objectMapper.convertValue(obj, UserFamilyDetails.class);

            familyDetails.add(temp);
        }


        UserPolicy userPolicy = new UserPolicy();

        userPolicy.setUser(user);
        userPolicy.setGroupPolicy(latest);
//        userPolicy.setUserFamilyDetails(dumbFamilyDetailsList);
        userPolicy.setUserFamilyDetails(familyDetails);
        if (requestBody.get("top-up") == null) {
            throw new CustomExceptionV2("More Args Required", 403, Map.of("Usage", "{top-up:Value}", "Content-Type", "application/json"));
        }
        userPolicy.setCoverage((Double) requestBody.get("top-up"));

        if(!Objects.isNull(userPolicyFromDb)) {

            userPolicy.setUserPolicyId(userPolicyFromDb.getUserPolicyId());

        }
        userPolicyRepository.save(userPolicy);

        return userPolicyRepository.findByGroupPolicyAndUser(latest,user);
    }
}
