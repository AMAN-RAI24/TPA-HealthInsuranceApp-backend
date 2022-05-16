package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PolicyController {
    @Autowired
    private GroupPolicyRepository groupPolicyRepository;
    @Autowired
    private LoginService loginService;
    @PostMapping("/add/policy")
    public String  addPolicy(@RequestBody GroupPolicy groupPolicy,@RequestHeader("Authorization") String jwt)
    {
        groupPolicy.setManager(loginService.getUser(jwt));
        groupPolicyRepository.save(groupPolicy);
        return "Added successfully";
    }
    @GetMapping("/get-policy")
    public GroupPolicy getPolicy(@RequestParam int id)
    {
        System.out.println(id);
        GroupPolicy groupPolicy = groupPolicyRepository.getById(id);
        System.out.println(groupPolicy.getPolicyName());
        return groupPolicy;
    }
}
