package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.responseModels.Policies;
import com.insurecorp.insureCorp.responseModels.PolicyAddedResponse;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PolicyController {
    @Autowired
    private GroupPolicyRepository groupPolicyRepository;
    @Autowired
    private LoginService loginService;
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
}
