package com.example.apartmentmanagement.dto.request.bill;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillItemRequest {
    private Long feeTypeId;
    private double quantity;
}
