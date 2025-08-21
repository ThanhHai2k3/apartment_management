package com.example.apartmentmanagement.dto.response.bill;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillItemResponse {
    private Long id;
    private Long feeTypeId;
    private String feeTypeName;
    private double quantity;
    private double amount;
}
