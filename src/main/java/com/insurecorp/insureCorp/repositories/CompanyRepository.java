package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    public List<Company> findByCompanyName(String companyName);
//    public Company findByCompanyName(String companyName);
}
