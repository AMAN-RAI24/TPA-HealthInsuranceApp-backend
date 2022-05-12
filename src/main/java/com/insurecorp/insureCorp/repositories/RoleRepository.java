package com.insurecorp.insureCorp.repositories;

import com.insurecorp.insureCorp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
        public List<Role> findByRole(String role);
}
