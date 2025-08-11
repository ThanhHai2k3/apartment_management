package com.example.apartmentmanagement.dto.response;

import com.example.apartmentmanagement.enums.ApartmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentResponse {
    private Long id;
    private String building;
    private String number;
    private int floor;
    private double area;
    private int capacity;
    private ApartmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
