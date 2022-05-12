package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.FamilyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyDetailsRepository extends JpaRepository<FamilyDetails,Integer> {
}
