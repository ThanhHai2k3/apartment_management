package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    boolean existsByEmail(String email);
    boolean existsByIdNumber(String idNumber);
}
