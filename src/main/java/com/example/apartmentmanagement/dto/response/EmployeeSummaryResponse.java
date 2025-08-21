package com.example.apartmentmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSummaryResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNum;
    private String levelName;
}
