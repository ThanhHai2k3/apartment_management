package com.example.apartmentmanagement.dto.response;

import com.example.apartmentmanagement.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDate dob;

    private List<ResidentApartmentHistoryResponse> histories;
}
