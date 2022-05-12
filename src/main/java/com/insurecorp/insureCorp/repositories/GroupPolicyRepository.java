package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPolicyRepository extends JpaRepository<GroupPolicy,Integer> {
}
