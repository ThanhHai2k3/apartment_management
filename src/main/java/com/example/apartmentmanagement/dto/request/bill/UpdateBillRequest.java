package com.example.apartmentmanagement.dto.request.bill;

import com.example.apartmentmanagement.enums.BillStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBillRequest {
    private LocalDate paymentDate;
    private BillStatus status;
}
