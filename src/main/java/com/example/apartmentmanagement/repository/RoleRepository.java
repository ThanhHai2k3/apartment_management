package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
