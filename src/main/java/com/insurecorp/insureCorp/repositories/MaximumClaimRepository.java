package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.MaximumClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaximumClaimRepository extends JpaRepository<MaximumClaim,Integer> {
}
