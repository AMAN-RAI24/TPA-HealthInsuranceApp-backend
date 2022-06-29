package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.InsuranceCompany;
import com.insurecorp.insureCorp.entities.Offer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Integer> {
    List<Offer> findOfferByGroupPolicy(GroupPolicy groupPolicy, Sort sort);
    List<Offer> findOfferByGroupPolicyAndStatus(GroupPolicy groupPolicy,String status);
    List<Offer> findOfferByInsuranceCompanyAndStatus(InsuranceCompany insuranceCompany,String status);
}
