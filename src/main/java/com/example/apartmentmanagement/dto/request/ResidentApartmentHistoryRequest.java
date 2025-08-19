package com.example.apartmentmanagement.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentApartmentHistoryRequest {
    private Long residentId;
    private Long apartmentId;
    private LocalDate startDate;
    private LocalDate endDate;
}
