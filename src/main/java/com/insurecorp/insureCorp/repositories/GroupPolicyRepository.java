package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.Company;
import com.insurecorp.insureCorp.entities.GroupPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPolicyRepository extends JpaRepository<GroupPolicy,Integer> {
//    GroupPolicy findGroupPolicyById (int id);
    List<GroupPolicy> findGroupPolicyByCompany (Company company);
    List<GroupPolicy> findGroupPolicyByCompanyOrderByCreationDateDesc(Company company);
}
