package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.exceptions.CustomExceptionV2;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.services.ManagerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class ManagerDashboardController {

    @Autowired
    ManagerDashboardService managerDashboardService;


    @GetMapping("/")
    ResponseEntity<List<GroupPolicy>> getAllPolicies( @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "6") Integer pageSize,
                                                      @RequestParam(defaultValue = "groupPolicyId") String sortBy){
        return new ResponseEntity<List<GroupPolicy>>(managerDashboardService.getAllPolicies(pageNo, pageSize, sortBy),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{policyId}")
    ResponseEntity<GroupPolicy> getPolicyById(@PathVariable Integer policyId){
        return new ResponseEntity<GroupPolicy>(managerDashboardService.getPolicyById(policyId),new HttpHeaders(), HttpStatus.OK);
    }
//    @GetMapping("/compare/{planId_1}")
//    ResponseEntity<Map<String,Object>> comparePlansByIdEdgeCase1(@PathVariable Integer planId_1) {
//        throw new CustomException("Parameter Required planId_1 and planId_2",400);
//    }

    @GetMapping({"/compare", "/compare/{planId_1}"})
    ResponseEntity<Map<String,Object>> comparePlansByIdEdgeCase() {
        throw new CustomExceptionV2("More Args Required",400,Map.of("Usage","/dashboard/compare/{planId_1}/{planId_2}"));
//        throw new CustomException("Usage: /dashboard/compare/{planId_1}/{planId_2}",400);
    }


    @GetMapping("/compare/{planId_1}/{planId_2}")
    ResponseEntity<Map<String,List>> comparePlansById(@PathVariable Integer planId_1, @PathVariable Integer planId_2){
        return ResponseEntity.ok().header("Content-Type","application/json").body(managerDashboardService.comparePlansById(planId_1, planId_2));
    }




}
