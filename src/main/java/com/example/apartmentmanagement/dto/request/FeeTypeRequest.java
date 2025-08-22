package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.enums.FeeTypeMode;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeRequest {
    private String name;
    private double unitPrice;
    private FeeTypeMode metered; // true: phí tính theo chỉ số, false: phí cố định
}
