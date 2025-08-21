package com.example.apartmentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apartmentmanagement.entity.FeeType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {
    Optional<FeeType> findByName(String name);
    boolean existsByName(String name);
}
