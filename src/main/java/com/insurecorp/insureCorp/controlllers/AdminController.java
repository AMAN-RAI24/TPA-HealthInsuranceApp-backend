package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.Company;
import com.insurecorp.insureCorp.entities.Hospital;
import com.insurecorp.insureCorp.entities.Role;
import com.insurecorp.insureCorp.responseModels.EntityAddedResponse;
import com.insurecorp.insureCorp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
