package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group_policy")
public class GroupPolicyController {
    @Autowired
    GroupPolicyRepository groupPolicyRepository;


    @PostMapping("/create")
    ResponseEntity<GroupPolicy> createGroupPolicy(@RequestBody GroupPolicy groupPolicy){
        return ResponseEntity.status(201).body(groupPolicyRepository.save(groupPolicy));
    }

}
