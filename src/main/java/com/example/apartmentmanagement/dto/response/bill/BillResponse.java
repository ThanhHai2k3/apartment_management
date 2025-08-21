package com.example.apartmentmanagement.dto.response.bill;

import com.example.apartmentmanagement.enums.BillStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private Long id;
    private Long apartmentId;
    private String apartmentNumber;
    private LocalDate period;
    private double totalAmount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private BillStatus status;
    private List<BillItemResponse> items;
}
