package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public List<User> findByEmail(String email);
    public List<User> findByMobileNumber(String mobileNumber);
    List<User> findUserByEmail(String email);
}
