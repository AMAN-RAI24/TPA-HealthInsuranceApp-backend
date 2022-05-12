package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.MaternityBenefits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaternityBenefitsRepository extends JpaRepository<MaternityBenefits,Integer> {
}
