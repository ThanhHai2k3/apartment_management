package com.example.apartmentmanagement.dto.response;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentResponse {
    private Long id;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDateTime dob;
    private LocalDateTime moveInDate;
    private LocalDateTime moveOutDate;
    private Long apartmentId;
    private String apartmentNumber;
}
