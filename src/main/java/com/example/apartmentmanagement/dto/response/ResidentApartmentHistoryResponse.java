package com.example.apartmentmanagement.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResidentApartmentHistoryResponse {
    private Long residentId;
    private Long apartmentId;
    private String apartmentNumber;
    private LocalDate startDate;
    private LocalDate endDate;
}
