package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.exceptions.CustomExceptionV2;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.responseModels.EntityAddedResponse;
import com.insurecorp.insureCorp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;


    @PostMapping("/add/hospital")
    private EntityAddedResponse addHospital(@RequestBody Hospital hospital)
    {
        adminService.addHospital(hospital);
        EntityAddedResponse response = new EntityAddedResponse();
        response.setMessage("Hospital Added successfully!");
        response.setName(hospital.getHospitalName());
        return response;
    }


    @PostMapping(value = "add/insurance-company",consumes = {MediaType.IMAGE_PNG_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    private EntityAddedResponse addInsuranceCompany(@RequestParam String insuranceCompanyString, @RequestParam MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("TESTING OBJ");
        InsuranceCompany insuranceCompany = objectMapper.readValue(insuranceCompanyString,InsuranceCompany.class);
        adminService.addInsuranceCompany(insuranceCompany);
        EntityAddedResponse response = new EntityAddedResponse();
        response.setMessage("Insurance company added successfully");
        response.setName(insuranceCompany.getName());
        System.out.println(file.getOriginalFilename());
        return response;
    }
    @PostMapping("/add/company")
    private EntityAddedResponse addCompany(@RequestBody Company company)
    {
        adminService.addCompany(company);
        EntityAddedResponse response = new EntityAddedResponse();
        response.setMessage("Company Added successfully!");
        response.setName(company.getCompanyName());
        return response;
    }
    @PostMapping("/add/role")
    private EntityAddedResponse addRole(@RequestBody Role role)
    {
        adminService.addRole(role);
        EntityAddedResponse response = new EntityAddedResponse();
        response.setMessage("Role Added successfully!");
        response.setName(role.getRole());
        return response;
    }

    @RequestMapping(value = {"/get","/get/"},method = {RequestMethod.POST,RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.PATCH})
    private void statusNotFound(){
        throw new CustomExceptionV2("Status not found",404,Map.of("Status Available",List.of("APPROVED","PENDING","REJECTED")));
    }

    @GetMapping("/get/{status}")
    private ResponseEntity<List<GroupPolicy>> getPolicyByStatus(@PathVariable String status){
        if (List.of("APPROVED","PENDING","REJECTED").contains(status)){
            return ResponseEntity.ok().body(adminService.getPolicyByStatus(status));
        }
        else{
            throw new CustomExceptionV2("Status not found",404,Map.of("Status Available",List.of("APPROVED","PENDING","REJECTED")));
        }
    }

    @RequestMapping(value = {"/set","/set/","/set/{id}","/set/{id}/"}, method = {RequestMethod.POST,RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.PATCH})
    private void statusOrdIdNotFound(){
        throw new CustomExceptionV2("Status not found",404,Map.of("Status Available",List.of("APPROVED","PENDING","REJECTED"),"Usage","/set/{id}/{status}","Request Method","PUT"));
    }

    @PutMapping("/set/{id}/{status}")
    private ResponseEntity<GroupPolicy> setStatusById(@PathVariable String status, @PathVariable Integer id){
        if (List.of("APPROVED","PENDING","REJECTED").contains(status)){
            return ResponseEntity.ok().body(adminService.setStatusById(status,id));
        }
        else{
            throw new CustomExceptionV2("Status not found",404,Map.of("Status Available",List.of("APPROVED","PENDING","REJECTED")));
        }
    }

}
