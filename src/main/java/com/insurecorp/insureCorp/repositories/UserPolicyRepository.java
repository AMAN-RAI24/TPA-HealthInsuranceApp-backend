package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.entities.UserPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPolicyRepository extends JpaRepository<UserPolicy,Integer> {
//    UserPolicy findByUser(User user);
    UserPolicy findByGroupPolicyAndUser(GroupPolicy groupPolicy,User user);
}
