package com.insurecorp.insureCorp.services;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.exceptions.CustomException;
import com.insurecorp.insureCorp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;
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

    public void addInsuranceCompany(InsuranceCompany insuranceCompany)
    {
        insuranceCompanyRepository.save(insuranceCompany);
    }
    public void addFile(MultipartFile file,String name) throws IOException {
        long timeStamp = System.currentTimeMillis();
        String projectId="us-gcp-ame-con-116-npd-1";
        String bucketName="hu-may-prod-insure-corp";
        System.out.println(file.getOriginalFilename());

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, String.valueOf(name));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());


        System.out.println("File " + file.getOriginalFilename() + " uploaded to bucket " + bucketName + " as " + name);
    }
}
