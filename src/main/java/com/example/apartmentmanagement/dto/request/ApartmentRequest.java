package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.enums.ApartmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentRequest {
    //@NotNull
    private String building;
    //@NotNull
    private String number;
    private int floor;
    private double area;
    private int capacity;
    private ApartmentStatus status;
}
