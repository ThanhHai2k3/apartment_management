package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.bill.CreateBillRequest;
import com.example.apartmentmanagement.dto.request.bill.UpdateBillRequest;
import com.example.apartmentmanagement.dto.response.bill.BillResponse;
import com.example.apartmentmanagement.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER') or hasAuthority('LEVEL_STAFF')")
    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody CreateBillRequest request) {
        return ResponseEntity.ok(billService.createBill(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER') or hasAuthority('LEVEL_STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long id, @RequestBody UpdateBillRequest request) {
        return ResponseEntity.ok(billService.updateBill(id, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or @customSecurity.isResidentLinkedToBill(authentication, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or @customSecurity.isResidentLinkedToApartment(authentication, #apartmentId)")
    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<BillResponse>> getBillsByApartment(@PathVariable Long apartmentId) {
        return ResponseEntity.ok(billService.getBillsByApartment(apartmentId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.noContent().build();
    }
}
