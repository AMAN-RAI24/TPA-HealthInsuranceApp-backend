package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Integer> {
    List<Hospital> findByTier(int tier);
    List<Hospital> findByTierLessThanEqual(int tier);
}
