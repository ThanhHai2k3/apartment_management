package com.example.apartmentmanagement.dto.response;

import com.example.apartmentmanagement.enums.FeeTypeMode;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeResponse {
    private Long id;
    private String name;
    private double unitPrice;
    private FeeTypeMode metered;
}
