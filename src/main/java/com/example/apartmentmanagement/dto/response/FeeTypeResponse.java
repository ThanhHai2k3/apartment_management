package com.example.apartmentmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeResponse {
    private Long id;
    private String name;
    private double unitPrice;
    private boolean isMetered;
}
