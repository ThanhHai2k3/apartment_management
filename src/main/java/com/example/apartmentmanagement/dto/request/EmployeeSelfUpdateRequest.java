package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSelfUpdateRequest {
    private String fullName;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDate dob;
}
