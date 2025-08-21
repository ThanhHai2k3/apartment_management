package com.example.apartmentmanagement.dto.request.bill;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillRequest {
    private Long apartmentId;
    private LocalDate period;
    private LocalDate dueDate;
    private List<BillItemRequest> items;
}
