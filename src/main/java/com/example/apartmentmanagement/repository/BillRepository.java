package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByApartmentId(Long apartmentId);
    List<Bill> findByApartmentIdAndPeriodBetween(Long apartmentId, LocalDate start, LocalDate end);
}
