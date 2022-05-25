package com.insurecorp.insureCorp.services;

import com.insurecorp.insureCorp.entities.Company;
import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.Hospital;
import com.insurecorp.insureCorp.entities.Role;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.repositories.CompanyRepository;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.HospitalRepository;
import com.insurecorp.insureCorp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
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

    public List<GroupPolicy> getPolicyByStatus(String status) {
        return groupPolicyRepository.findGroupPolicyByStatus(status);
    }

    public GroupPolicy setStatusById(String status, Integer id) {
        GroupPolicy groupPolicy = groupPolicyRepository.findById(id).orElseThrow(() -> new CustomException("Id Not Found",404));
        groupPolicy.setStatus(status);
        return groupPolicyRepository.save(groupPolicy);
    }
}
