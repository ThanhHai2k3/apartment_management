package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.enums.ApartmentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentRequest {
    private String building;
    private String number;
    private int floor;
    private double area;
    private int capacity;
    private ApartmentStatus status;
}
