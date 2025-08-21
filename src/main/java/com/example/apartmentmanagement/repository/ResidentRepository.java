package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    boolean existsByEmail(String email);
    boolean existsByIdNumber(String idNumber);
    boolean existsByUserId(Long userId);

    @Query("""
        SELECT rah.resident 
        FROM ResidentApartmentHistory rah 
        WHERE rah.apartment.id = :apartmentId 
        AND rah.endDate IS NULL
    """)
    List<Resident> findCurrentResidentsByApartmentId(@Param("apartmentId") Long apartmentId);
}
