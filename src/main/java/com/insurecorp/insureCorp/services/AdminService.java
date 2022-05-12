package com.insurecorp.insureCorp.services;

import com.insurecorp.insureCorp.entities.Company;
import com.insurecorp.insureCorp.entities.Hospital;
import com.insurecorp.insureCorp.entities.Role;
import com.insurecorp.insureCorp.repositories.CompanyRepository;
import com.insurecorp.insureCorp.repositories.HospitalRepository;
import com.insurecorp.insureCorp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RoleRepository roleRepository;
    public void addHospital(Hospital hospital)
    {
        hospitalRepository.save(hospital);
    }
    public void addCompany(Company company)
    {
        companyRepository.save(company);
    }
    public void addRole(Role role)
    {
        role.setRole("ROLE_"+role.getRole());
        roleRepository.save(role);
    }

}
