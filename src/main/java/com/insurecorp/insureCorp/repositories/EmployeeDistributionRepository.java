package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.EmployeeDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDistributionRepository extends JpaRepository<EmployeeDistribution,Integer> {
}
