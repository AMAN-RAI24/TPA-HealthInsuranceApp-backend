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

import java.util.*;

@Service
public class ManagerDashboardService {

    @Autowired
    GroupPolicyRepository groupPolicyRepository;


    public List<GroupPolicy> getAllPolicies() {
//        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<GroupPolicy> pagedResult = groupPolicyRepository.findAll(paging);
        List<GroupPolicy> pagedResult = groupPolicyRepository.findGroupPolicyByStatus("APPROVED");
//        if(pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<>();
//        }

        return pagedResult;
    }

    public GroupPolicy getPolicyById(Integer policyId) {
        return groupPolicyRepository.findById(policyId).orElseThrow(() -> new CustomException("Policy Id Not Found",404));
    }

    public Map<String,List> comparePlansById(Integer planId_1, Integer planId_2) {
        GroupPolicy gp1 =  groupPolicyRepository.findById(planId_1).orElseThrow(() -> new CustomException("Policy 1 Id Not Found",404));
        GroupPolicy gp2 =  groupPolicyRepository.findById(planId_2).orElseThrow(() -> new CustomException("Policy 2 Id Not Found",404));
//        System.out.println(gp1);
//        Map<String,Object> mb = (Map<String, Object>) gp1;
//        System.out.println(mb.get("policyName"));

        ObjectMapper oMapper = new ObjectMapper();
//        Fields excluded from comparison
//        gp1.setGroupPolicyId(null);
        gp1.setManager(null);

        Map<String,Object> obj1 = oMapper.convertValue(gp1, Map.class);
        Map<String,Object> obj2 = oMapper.convertValue(gp2, Map.class);

        Map<String,List> payload = new LinkedHashMap<>();



        Iterator<Map.Entry<String, Object>> itr1 = obj1.entrySet().iterator();
        Iterator<Map.Entry<String, Object>> itr2 = obj2.entrySet().iterator();

//        System.out.println(oMapper.convertValue(obj1.get("maximumClaim"),Map.class).get("bypassSurgery"));

        while(itr1.hasNext() && itr2.hasNext())
        {
            Map.Entry<String, Object> entry1 = itr1.next();
            Map.Entry<String, Object> entry2 = itr2.next();

            try{
                Map<String,Object> objSub1 = oMapper.convertValue(entry1.getValue(), Map.class);
                Map<String,Object> objSub2 = oMapper.convertValue(entry2.getValue(), Map.class);



                Iterator<Map.Entry<String, Object>> itrSub1 = objSub1.entrySet().iterator();
                Iterator<Map.Entry<String, Object>> itrSub2 = objSub2.entrySet().iterator();
                while(itrSub1.hasNext() && itrSub2.hasNext()){
                    Map.Entry<String, Object> entrySub1 = itrSub1.next();
                    Map.Entry<String, Object> entrySub2 = itrSub2.next();
                    if (entrySub1.getValue() != null && entrySub2.getValue() != null && entrySub1.getKey().indexOf("Id") == -1){
                        payload.put(entrySub1.getKey(),List.of(entrySub1.getValue(),entrySub2.getValue()));
                    }
                }
            }catch (Exception e){
                if (entry1.getValue() != null && entry2.getValue() != null && entry1.getKey().indexOf("Id") == -1){
                    payload.put(entry1.getKey(),List.of(entry1.getValue(),entry2.getValue()));
                }
            }


        }


        return payload;
    }

//    Map<String, List> play(){}
}
