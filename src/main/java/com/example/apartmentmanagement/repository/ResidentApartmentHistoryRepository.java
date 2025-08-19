package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.ResidentApartmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentApartmentHistoryRepository extends JpaRepository<ResidentApartmentHistory, Long> {
    List<ResidentApartmentHistory> findByResidentId(Long residentId);

    // Lịch sử đang active (chưa có endDate) của 1 cư dân
    Optional<ResidentApartmentHistory> findByResidentIdAndEndDateIsNull(Long residentId);

    // Toàn bộ lịch sử của 1 cư dân (mới nhất trước)
    List<ResidentApartmentHistory> findByResidentIdOrderByStartDateDesc(Long residentId);

    @Query("""
        SELECT CASE WHEN COUNT(h1) > 0 THEN TRUE ELSE FALSE END
        FROM ResidentApartmentHistory h1
        JOIN ResidentApartmentHistory h2
            ON h1.apartment.id = h2.apartment.id
        WHERE h1.resident.id = :residentId1
          AND h2.resident.id = :residentId2
          AND (h1.startDate <= COALESCE(h2.endDate, CURRENT_DATE))
          AND (h2.startDate <= COALESCE(h1.endDate, CURRENT_DATE))
    """)
    boolean existsCohabitationHistory(Long residentId1, Long residentId2);

    @Query("""
        SELECT CASE WHEN COUNT(h1) > 0 THEN TRUE ELSE FALSE END
        FROM ResidentApartmentHistory h1
        JOIN ResidentApartmentHistory h2
            ON h1.apartment.id = h2.apartment.id
        WHERE h1.resident.id = :residentId1
          AND h2.resident.id = :residentId2
          AND h1.endDate IS NULL
          AND h2.endDate IS NULL
    """)
    boolean existsCohabitationCurrent(Long residentId1, Long residentId2);
}
