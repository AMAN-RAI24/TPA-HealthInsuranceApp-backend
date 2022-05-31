package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.Claim;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim,Integer> {
    List<Claim> findByUserName(String userName, Sort createdAt);
}
