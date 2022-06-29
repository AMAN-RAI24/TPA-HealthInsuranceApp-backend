package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany,Integer> {
List<InsuranceCompany> findInsuranceCompanyByName(String name);
}
