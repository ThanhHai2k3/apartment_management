package com.example.apartmentmanagement.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeRequest {
    private String name;
    private double unitPrice;
    private boolean isMetered; // true: phí tính theo chỉ số, false: phí cố định
}
