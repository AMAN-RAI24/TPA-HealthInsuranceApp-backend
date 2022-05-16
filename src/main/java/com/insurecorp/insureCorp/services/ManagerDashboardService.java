package com.insurecorp.insureCorp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManagerDashboardService {

    @Autowired
    GroupPolicyRepository groupPolicyRepository;


    public List<GroupPolicy> getAllPolicies(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<GroupPolicy> pagedResult = groupPolicyRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<GroupPolicy>();
        }
    }

    public GroupPolicy getPolicyById(Integer policyId) {
        return groupPolicyRepository.findById(policyId).orElseThrow(() -> new CustomException("Policy Id Not Found",404));
    }

    public Map<String,Object> comparePlansById(Integer planId_1, Integer planId_2) {
        GroupPolicy gp1 =  groupPolicyRepository.findById(planId_1).orElseThrow(() -> new CustomException("Policy 1 Id Not Found",404));
        GroupPolicy gp2 =  groupPolicyRepository.findById(planId_2).orElseThrow(() -> new CustomException("Policy 2 Id Not Found",404));
//        System.out.println(gp1);
//        Map<String,Object> mb = (Map<String, Object>) gp1;
//        System.out.println(mb.get("policyName"));

        ObjectMapper oMapper = new ObjectMapper();
        Map<String,Object> mb = oMapper.convertValue(gp1, Map.class);
        return null;
    }
}
