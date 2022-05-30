package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.FamilyDetails;
import com.insurecorp.insureCorp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyDetailsRepository extends JpaRepository<FamilyDetails,Integer> {

}
