package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentRequest {
    private String fullName;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDate dob;
}
