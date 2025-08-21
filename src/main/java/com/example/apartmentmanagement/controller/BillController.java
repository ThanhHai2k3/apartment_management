package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.bill.CreateBillRequest;
import com.example.apartmentmanagement.dto.request.bill.UpdateBillRequest;
import com.example.apartmentmanagement.dto.response.bill.BillResponse;
import com.example.apartmentmanagement.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody CreateBillRequest request) {
        return ResponseEntity.ok(billService.createBill(request));
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long billId, @RequestBody UpdateBillRequest request) {
        return ResponseEntity.ok(billService.updateBill(billId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<BillResponse>> getBillsByApartment(@PathVariable Long apartmentId) {
        return ResponseEntity.ok(billService.getBillsByApartment(apartmentId));
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}
