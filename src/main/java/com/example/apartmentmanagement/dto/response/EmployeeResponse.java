package com.example.apartmentmanagement.dto.response;

import com.example.apartmentmanagement.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String levelName;
    private String username;
}
