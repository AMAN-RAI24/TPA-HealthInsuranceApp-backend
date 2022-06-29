package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.InsuranceCompanyRepository;
import com.insurecorp.insureCorp.repositories.OfferRepository;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.responseModels.InsuranceAcceptedPlans;
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

    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;

    @GetMapping("/profile")
    Map<String,Object> getProfile(@RequestHeader("Authorization") String jwt) {
        User user = loginService.getUser(jwt);
        if (Objects.isNull(user)){
            throw new CustomException("Invalid Token",403);
        }
        Map<String,Object> payload = new HashMap<>();
        payload.put("userDetails",Map.of("employeeId",user.getEmployeeId(),"name", user.getName(),"dob",user.getDate(),"company",user.getCompany()==null?user.getName():user.getCompany().getCompanyName(),"mobileNumber",user.getMobileNumber()));
//        List<GroupPolicy> list = user.getCompany().getGroupPolicies();
        List<GroupPolicy> list = groupPolicyRepository.findGroupPolicyByCompanyAndStatus(user.getCompany(),"APPROVED");
        if(list.isEmpty() ){
            throw new CustomException("Company hasn't bought any policies yet.",404);
        }


        GroupPolicy latest = list.get(0);


        for(GroupPolicy groupPolicy: list){
            if (groupPolicy.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicy;
            }
        }

        UserPolicy userPolicy =  userPolicyRepository.findByGroupPolicyAndUser(latest,user);
        payload.put("planDetails", Map.of("policyName",latest.getPolicyName(),"coverage",latest.getCoverage(),"topUp",Objects.isNull(userPolicy)?0.0:userPolicy.getCoverage(),"date",latest.getCreationDate()));

        if ("ROLE_MANAGER".equals(user.getRole().getRole())){
            List<GroupPolicy> previousPlans = groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
            List<Map> payloadPreviousPlans = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> temp;
            for(GroupPolicy previousPlan: previousPlans){
                temp = objectMapper.convertValue(previousPlan,Map.class);
                temp.remove("company");
                temp.remove("manager");
                payloadPreviousPlans.add(temp);
            }
            payload.put("previousPlans",payloadPreviousPlans);

        }else{

        }
        if (Objects.isNull(userPolicy)){
            payload.put("familyDetails",new ArrayList<>());
            return payload;
        }


        payload.put("familyDetails", userPolicy.getUserFamilyDetails().isEmpty()? new ArrayList<>() : userPolicy.getUserFamilyDetails());
        return payload;
    }
    @GetMapping("/insurance-profile")
    List<InsuranceAcceptedPlans> getInsuranceProfile(@RequestHeader("Authorization") String jwt)
    {
        List<InsuranceAcceptedPlans> plans = new ArrayList<>();
        User user = loginService.getUser(jwt);
        InsuranceCompany insuranceCompany = insuranceCompanyRepository.findInsuranceCompanyByName(user.getName()).get(0);
        List<Offer> offers = offerRepository.findOfferByInsuranceCompanyAndStatus(insuranceCompany,"ACCEPTED");
        List<InsuranceAcceptedPlans> acceptedPlans = new ArrayList<>();

        for(Offer offer : offers)
        {
            InsuranceAcceptedPlans plan = new InsuranceAcceptedPlans();
            plan.setPolicyName(offer.getGroupPolicy().getPolicyName());
            plan.setId(offer.getGroupPolicy().getGroupPolicyId());
            plan.setCoverage(offer.getGroupPolicy().getCoverage());
            plan.setCreationDate(offer.getGroupPolicy().getCreationDate());
            plan.setStatus("ACCEPTED");
            acceptedPlans.add(plan);
        }
        System.out.println("size is "+offers.size());
        return acceptedPlans;
    }

}
